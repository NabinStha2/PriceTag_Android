package com.example.price_tag.feature.cart.presentation

import com.example.price_tag.core.common.domain.model.CommonResponse
import com.example.price_tag.feature.cart.data.model.AddCartItemRequestDto
import com.example.price_tag.feature.cart.data.model.UpdateCartItemQuantityRequestDto
import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.utilites.ResourceState

class CartContract {

    sealed interface CartIntent {
        data object GetAllCartItems : CartIntent

        data class AddCartItemsToCart(val addCartItemRequestDto: AddCartItemRequestDto) : CartIntent

        data object ResetAddItemToCartState : CartIntent

        data class RemoveCartItem(val cartItemId: Long) : CartIntent

        data object RefreshCart : CartIntent

        data class UpdateCartItemQuantity(val updateCartItemQuantityRequestDto: UpdateCartItemQuantityRequestDto) :
            CartIntent
    }

    data class CartState(
        val getAllCartItems: ResourceState<Cart> = ResourceState.Initial,
        val addItemToCartState: ResourceState<CommonResponse> = ResourceState.Initial,
        val updateCartItemQuantityState: ResourceState<CommonResponse> = ResourceState.Initial,
        val isRefreshing: Boolean = false,
        val isUpdating: Boolean = false,
        val removingItemIds: Set<Long> = emptySet(), // items currently being removed
    )


    sealed interface CartEffect {
        data class NavigateToCheckout(val cartId: Long) : CartEffect
        data class ShowSnackBar(val message: String) : CartEffect
    }

}