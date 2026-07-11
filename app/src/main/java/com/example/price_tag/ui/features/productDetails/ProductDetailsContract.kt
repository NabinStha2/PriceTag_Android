package com.example.price_tag.ui.features.productDetails

import com.example.price_tag.domain.model.product.ProductDetailsRatingReviews
import com.example.price_tag.domain.model.product.ProductDetailsRatingReviewsData
import com.example.price_tag.domain.model.product.ProductVariants
import com.example.price_tag.domain.model.product.SingleProductDetails
import com.example.utilites.ResourceState

class ProductDetailsContract {
    sealed interface ProductDetailsIntent {
        data class GetSingleProductDetails(val productId: Long) : ProductDetailsIntent

        data class GetSingleProductDetailsRatingReviews(
            val productId: Long,
            val page: Int? = 1,
            val limit: Int? = 10,
            val sortBy: String? = "createdAt",
            val order: String? = "desc",
            val showLimited: Boolean? = false,
        ) : ProductDetailsIntent

        data class NavigationToRatingReview(val productId: Long) : ProductDetailsIntent
    }

    data class ProductDetailsState(
        val getSingleProductDetailsData: ResourceState<SingleProductDetails> = ResourceState.Initial,
        val getSingleProductDetailsRatingReviews: ResourceState<ProductDetailsRatingReviews> = ResourceState.Loading,
        val reviewListData: List<ProductDetailsRatingReviewsData> = emptyList(),
        val limitedReviewListData: List<ProductDetailsRatingReviewsData> = emptyList(),
        val isLoadingMoreReviews: Boolean = false,
        val currentReviewPage: Int = 0,
        val isLastReviewPage: Boolean = false,
        val selectedColor: String? = null,
        val selectedSize: Int? = null,
        val selectedVariant: ProductVariants? = null,
    )

    sealed interface ProductDetailsEffect {
        data class NavigateToRatingReviewView(val productId: Long) : ProductDetailsEffect
    }
}