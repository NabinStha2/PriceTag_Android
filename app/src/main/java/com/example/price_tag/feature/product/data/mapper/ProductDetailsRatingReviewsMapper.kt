package com.example.price_tag.feature.product.data.mapper

import com.example.price_tag.core.common.data.mapper.toDomain
import com.example.price_tag.feature.product.data.model.ProductDetailsRatingReviewsDataDto
import com.example.price_tag.feature.product.data.model.ProductDetailsRatingReviewsResponseDto
import com.example.price_tag.core.common.domain.model.PaginationModel
import com.example.price_tag.feature.product.domain.model.ProductDetailsRatingReviews
import com.example.price_tag.feature.product.domain.model.ProductDetailsRatingReviewsData

fun ProductDetailsRatingReviewsResponseDto.toDomain(): ProductDetailsRatingReviews {
    return ProductDetailsRatingReviews(
        productDetailsRatingReviewsData = data?.map {
            it.toDomain()
        } ?: emptyList(),
        pagination = pagination?.toDomain() ?: PaginationModel(
            currentPage = 0,
            pageSize = 0,
            totalItems = 0L,
            totalPages = 0,
            hasNext = false,
            hasPrevious = false,
        ),
    );

}

fun ProductDetailsRatingReviewsDataDto.toDomain(): ProductDetailsRatingReviewsData {
    return ProductDetailsRatingReviewsData(
        id = id ?: 0L,
        rating = rating ?: 0.0,
        reviews = review ?: "",
        productId = productID ?: 0L,
        userId = userID ?: 0L,
        userName = userFullName ?: "",
        productName = productName ?: "",
    );
}