package com.example.price_tag.feature.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.feature.cart.domain.usecase.GetAllCartItemsUseCase
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(private val getAllCartItemsUseCase: GetAllCartItemsUseCase) :
    ViewModel() {

    private val _allCartItems: MutableStateFlow<CartContract.CartState> =
        MutableStateFlow(CartContract.CartState())
    val allCartItems: StateFlow<CartContract.CartState> = _allCartItems.asStateFlow()


    init {
        onIntent(CartContract.CartIntent.GetAllCartItems)
    }

    fun onIntent(cartIntent: CartContract.CartIntent) {
        when (cartIntent) {
            is CartContract.CartIntent.GetAllCartItems -> {
                getAllCartItems()
            }
        }
    }

    private fun getAllCartItems() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllCartItemsUseCase().collectLatest { response ->
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
}