package com.example.price_tag.feature.cart.presentation

import com.example.price_tag.feature.product.domain.model.Products
import com.example.utilites.ResourceState

class CartContract {

    sealed interface CartIntent {
        data class GetAllCartItems(
            val cartId: Long,
        ) : CartIntent
    }

    data class CartState(
        val getAllSubCategoryProductsData: ResourceState<Products> = ResourceState.Initial,
    )


    sealed interface CartEffect {
        data class NavigateToCheckout(val cartId: Long) : CartEffect
    }

}