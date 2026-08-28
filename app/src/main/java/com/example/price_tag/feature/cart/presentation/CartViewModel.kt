package com.example.price_tag.feature.cart.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.feature.cart.data.model.AddCartItemRequestDto
import com.example.price_tag.feature.cart.data.model.UpdateCartItemQuantityRequestDto
import com.example.price_tag.feature.cart.domain.usecase.AddItemToCartUseCase
import com.example.price_tag.feature.cart.domain.usecase.GetAllCartItemsUseCase
import com.example.price_tag.feature.cart.domain.usecase.RemoveCartItemUseCase
import com.example.price_tag.feature.cart.domain.usecase.UpdateCartItemQuantityUseCase
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
    private val navigationCoordinator: NavigationCoordinator,
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val addItemToCartUseCase: AddItemToCartUseCase,
    private val removeCartItemUseCase: RemoveCartItemUseCase,
    private val updateCartItemQuantityUseCase: UpdateCartItemQuantityUseCase,
) : ViewModel() {
    private val _cartState: MutableStateFlow<CartContract.CartState> =
        MutableStateFlow(CartContract.CartState())
    val cartState: StateFlow<CartContract.CartState> = _cartState.asStateFlow()

    private val _effect: Channel<CartContract.CartEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()


    init {
        Log.d("CartViewModel",
              "init: Fetching cart items")
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
                _cartState.update { state ->
                    state.copy(addItemToCartState = ResourceState.Initial)
                }
            }

            is CartContract.CartIntent.RemoveCartItem -> {
                removeCartItem(cartIntent.cartItemId)
            }

            is CartContract.CartIntent.RefreshCart -> {
                getAllCartItems(isPullToRefresh = cartIntent.pullToRefresh)
            }

            is CartContract.CartIntent.UpdateCartItemQuantity -> {
                updateCartItemQuantity(cartIntent.updateCartItemQuantityRequestDto)
            }

            is CartContract.CartIntent.CheckoutClicked -> {
                checkoutClicked(cartIntent.cartId)
            }
        }
    }

    private fun getAllCartItems(
        isPullToRefresh: Boolean = false,
        isUpdating: Boolean = false,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("CartViewModel",
                  "getAllCartItems: Starting fetch")
            getAllCartItemsUseCase().collectLatest { response ->
                Log.d("CartViewModel",
                      "getAllCartItems: Received response: $response")
                _cartState.update { state ->
                    when (response) {
                        is ResourceState.Loading -> {
                            if (isPullToRefresh) { // pull-to-refresh: keep existing list visible, just flip the flag
                                state.copy(isRefreshing = true)
                            } else if (isUpdating) {
                                state.copy(isUpdating = true)
                            } else { // normal/initial load: let the full-screen Loading UI show
                                state.copy(getAllCartItems = response)
                            }
                        }

                        is ResourceState.Success -> {
                            state.copy(
                                getAllCartItems = response,
                                isRefreshing = false,
                                isUpdating = false,
                            )
                        }


                        is ResourceState.Error -> {
                            state.copy(
                                getAllCartItems = response,
                                isRefreshing = false,
                                isUpdating = false,
                            )
                        }

                        else -> {
                            state.copy(
                                isRefreshing = false,
                                isUpdating = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun addItemsToCart(addCartItemRequestDto: AddCartItemRequestDto) {
        if (_cartState.value.addItemToCartState is ResourceState.Loading) return

        viewModelScope.launch(Dispatchers.IO) {
            addItemToCartUseCase(addCartItemRequestDto = addCartItemRequestDto).collectLatest { response ->
                _cartState.update { state ->
                    state.copy(addItemToCartState = response)
                }
                when (response) {
                    is ResourceState.Success -> {
                        Log.d("CartViewModel",
                              "addItemsToCart: Success, refreshing cart")
                        _effect.send(CartContract.CartEffect.ShowSnackBar(response.data.message
                                                                          ?: ""))
                        getAllCartItems(isUpdating = true)
                    }

                    is ResourceState.Error -> {
                        Log.e("CartViewModel",
                              "addItemsToCart: Error: ${response.error}")
                        _effect.send(CartContract.CartEffect.ShowSnackBar(response.error))
                    }

                    else -> {}
                }
            }
        }
    }

    private fun removeCartItem(cartItemId: Long) {
        if (cartItemId in _cartState.value.removingItemIds) return
        viewModelScope.launch(Dispatchers.IO) {
            _cartState.update { it.copy(removingItemIds = it.removingItemIds + cartItemId) }

            removeCartItemUseCase(cartItemId = cartItemId).collectLatest { response ->
                when (response) {
                    is ResourceState.Success -> {
                        Log.d("CartViewModel",
                              "removeCartItem: Success, removed cart item")
                        _effect.send(CartContract.CartEffect.ShowSnackBar(response.data.message
                                                                          ?: ""))
                        _cartState.update { it.copy(removingItemIds = it.removingItemIds - cartItemId) }

                        getAllCartItems(isUpdating = true)
                    }

                    is ResourceState.Error -> {
                        Log.e("CartViewModel",
                              "removeCartItem: Error: ${response.error}")
                        _effect.send(CartContract.CartEffect.ShowSnackBar(response.error))
                        _cartState.update { it.copy(removingItemIds = it.removingItemIds - cartItemId) }
                    }

                    else -> {
                        _cartState.update { it.copy(removingItemIds = it.removingItemIds - cartItemId) }
                    }
                }
            }
        }
    }

    private fun updateCartItemQuantity(updateCartItemQuantityRequestDto: UpdateCartItemQuantityRequestDto) {
        if (_cartState.value.updateCartItemQuantityState is ResourceState.Loading) return

        viewModelScope.launch(Dispatchers.IO) {
            updateCartItemQuantityUseCase(updateCartItemQuantityRequestDto = updateCartItemQuantityRequestDto).collectLatest { response ->
                _cartState.update { state ->
                    state.copy(updateCartItemQuantityState = response)
                }
                when (response) {
                    is ResourceState.Success -> {
                        Log.d("CartViewModel",
                              "updateCartItemQuantity: Success, updated cart item")
                        _effect.send(CartContract.CartEffect.ShowSnackBar(response.data.message
                                                                          ?: ""))

                        getAllCartItems(isUpdating = true)
                    }

                    is ResourceState.Error -> {
                        Log.e("CartViewModel",
                              "updateCartItemQuantity: Error: ${response.error}")
                        _effect.send(CartContract.CartEffect.ShowSnackBar(response.error))
                    }

                    else -> {}
                }
            }
        }
    }

    private fun checkoutClicked(cartId: Long) {
        val cartState = _cartState.value.getAllCartItems
        if (cartState !is ResourceState.Success) return

        val cart = cartState.data
        val isBusy =
            _cartState.value.isUpdating || _cartState.value.updateCartItemQuantityState is ResourceState.Loading || _cartState.value.removingItemIds.isNotEmpty()

        if (isBusy || cart.items.isEmpty() || cart.cartId != cartId) return

        viewModelScope.launch(Dispatchers.IO) {
            navigationCoordinator.navigate(NavigationItem.CheckoutView(cartId = cartId))
        }
    }

}
