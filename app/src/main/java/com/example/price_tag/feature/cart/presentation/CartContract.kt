package com.example.price_tag.feature.cart.presentation

import com.example.price_tag.core.common.domain.model.CommonSuccessResponse
import com.example.price_tag.feature.cart.data.model.AddCartItemRequestDto
import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.utilites.ResourceState

class CartContract {

    sealed interface CartIntent {
        data object GetAllCartItems : CartIntent

        data class AddCartItemsToCart(val addCartItemRequestDto: AddCartItemRequestDto) : CartIntent

        data object ResetAddItemToCartState : CartIntent
    }

    data class CartState(
        val getAllCartItems: ResourceState<Cart> = ResourceState.Initial,
        val addItemToCartState: ResourceState<CommonSuccessResponse> = ResourceState.Initial,
    )


    sealed interface CartEffect {
        data class NavigateToCheckout(val cartId: Long) : CartEffect
        data class ShowSnackBar(val message: String) : CartEffect
    }

}