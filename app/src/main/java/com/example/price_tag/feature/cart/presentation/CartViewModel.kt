package com.example.price_tag.feature.cart.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.feature.cart.data.model.AddCartItemRequestDto
import com.example.price_tag.feature.cart.domain.usecase.AddItemToCartUseCase
import com.example.price_tag.feature.cart.domain.usecase.GetAllCartItemsUseCase
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val addItemToCartUseCase: AddItemToCartUseCase,
) : ViewModel() {

    private val _allCartItems: MutableStateFlow<CartContract.CartState> =
        MutableStateFlow(CartContract.CartState())
    val allCartItems: StateFlow<CartContract.CartState> = _allCartItems.asStateFlow()

    private val _effect: Channel<CartContract.CartEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()


    init {
        Log.d("CartViewModel", "init: Fetching cart items")
        onIntent(CartContract.CartIntent.GetAllCartItems)
    }

    fun onIntent(cartIntent: CartContract.CartIntent) {
        when (cartIntent) {
            is CartContract.CartIntent.GetAllCartItems -> {
                getAllCartItems()
            }

            is CartContract.CartIntent.AddCartItemsToCart -> {
                addItemsToCart(cartIntent.addCartItemRequestDto)
            }

            is CartContract.CartIntent.ResetAddItemToCartState -> {
                _allCartItems.update { state ->
                    state.copy(addItemToCartState = ResourceState.Initial)
                }
            }
        }
    }

    private fun getAllCartItems() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("CartViewModel", "getAllCartItems: Starting fetch")
            getAllCartItemsUseCase().collectLatest { response ->
                Log.d("CartViewModel", "getAllCartItems: Received response: $response")
                _allCartItems.update { state ->
                    when (response) {
                        is ResourceState.Success -> {
                            state.copy(getAllCartItems = response)
                        }

                        else -> {
                            state.copy(getAllCartItems = response)
                        }
                    }
                }
            }
        }
    }

    private fun addItemsToCart(addCartItemRequestDto: AddCartItemRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            addItemToCartUseCase(addCartItemRequestDto = addCartItemRequestDto).collectLatest { response ->
                _allCartItems.update { state ->
                    state.copy(addItemToCartState = response)
                }
                when (response) {
                    is ResourceState.Success -> {
                        Log.d("CartViewModel", "addItemsToCart: Success, refreshing cart")
                        _effect.send(CartContract.CartEffect.ShowSnackBar(response.data.message))
                        getAllCartItems()
                    }

                    is ResourceState.Error -> {
                        Log.e("CartViewModel", "addItemsToCart: Error: ${response.error}")
                        _effect.send(CartContract.CartEffect.ShowSnackBar(response.error))
                    }

                    else -> {}
                }
            }
        }
    }


}
