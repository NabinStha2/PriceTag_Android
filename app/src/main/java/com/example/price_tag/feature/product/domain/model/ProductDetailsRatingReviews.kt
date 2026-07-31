package com.example.price_tag.feature.product.domain.model

import com.example.price_tag.core.common.domain.model.PaginationModel

data class ProductDetailsRatingReviews(
    val productDetailsRatingReviewsData: List<ProductDetailsRatingReviewsData>,
    val pagination: PaginationModel,
)

data class ProductDetailsRatingReviewsData(
    val id: Long,
    val rating: Double,
    val reviews: String,
    val userId: Long,
    val productId: Long,
    val userName: String,
    val productName: String,
)