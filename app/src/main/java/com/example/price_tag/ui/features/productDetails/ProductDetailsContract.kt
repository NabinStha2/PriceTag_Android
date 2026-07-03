package com.example.price_tag.ui.features.productDetails

import com.example.price_tag.data.model.product_details.SingleProductDetailsResponseDto
import com.example.price_tag.domain.model.product.ProductVariants
import com.example.price_tag.domain.model.product.SingleProductDetails
import com.example.utilites.ResourceState

class ProductDetailsContract {
    sealed interface ProductDetailsIntent {
        data class GetSingleProductDetails(val productId: Long) : ProductDetailsIntent
    }

    data class ProductDetailsState(
        val getSingleProductDetailsData: ResourceState<SingleProductDetails> = ResourceState.Initial,
        val selectedColor: String? = null,
        val selectedSize: Int? = null,
        val selectedVariant: ProductVariants? = null,
    )

    sealed interface ProductDetailsEffect { //        data class ShowErrorMessage(val message: String) : ProductDetailsEffect
    }
}