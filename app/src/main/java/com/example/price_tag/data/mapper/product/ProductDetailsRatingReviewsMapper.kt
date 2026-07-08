package com.example.price_tag.data.mapper.product

import com.example.price_tag.data.mapper.common.toDomain
import com.example.price_tag.data.model.product_details.ProductDetailsRatingReviewsDataDto
import com.example.price_tag.data.model.product_details.ProductDetailsRatingReviewsResponseDto
import com.example.price_tag.domain.model.common.PaginationModel
import com.example.price_tag.domain.model.product.ProductDetailsRatingReviews
import com.example.price_tag.domain.model.product.ProductDetailsRatingReviewsData

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