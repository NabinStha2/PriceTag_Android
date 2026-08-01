package com.example.price_tag.feature.cart.presentation

import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.utilites.ResourceState

class CartContract {

    sealed interface CartIntent {
        data object GetAllCartItems : CartIntent
    }

    data class CartState(
        val getAllCartItems: ResourceState<Cart> = ResourceState.Initial,
    )


    sealed interface CartEffect {
        data class NavigateToCheckout(val cartId: Long) : CartEffect
    }

}