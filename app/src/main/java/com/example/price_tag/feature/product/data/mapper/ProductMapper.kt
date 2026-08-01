package com.example.price_tag.feature.product.data.mapper

import com.example.price_tag.core.common.data.mapper.toDomain
import com.example.price_tag.core.common.domain.model.PaginationModel
import com.example.price_tag.feature.product.data.model.ProductDataDto
import com.example.price_tag.feature.product.data.model.ProductResponseDto
import com.example.price_tag.feature.product.domain.model.ProductData
import com.example.price_tag.feature.product.domain.model.Products

fun ProductResponseDto.toDomain(): Products {
    return Products(
        products = data?.map { it.toDomain() } ?: emptyList(),
        pagination = pagination?.toDomain() ?: PaginationModel(
            currentPage = 0,
            pageSize = 0,
            totalItems = 0L,
            totalPages = 0,
            hasNext = false,
            hasPrevious = false,
        ),
    )
}

fun ProductDataDto.toDomain(): ProductData {
    return ProductData(
        id = id ?: 0L,
        name = name.orEmpty(),
        slug = slug.orEmpty(),
        description = description ?: "",
        shortDescription = shortDescription ?: "",
        imageUrl = primaryImageURL ?: "",
        basePrice = basePrice ?: 0.0,
        discountedPrice = discountedPrice ?: basePrice ?: 0.0,
        brand = brand ?: "",
        rating = totalRating ?: 0.0,
        totalReview = totalReview ?: 0L,
    )
}