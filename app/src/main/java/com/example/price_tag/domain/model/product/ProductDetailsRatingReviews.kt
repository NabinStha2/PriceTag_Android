package com.example.price_tag.domain.model.product

import com.example.price_tag.domain.model.common.PaginationModel

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