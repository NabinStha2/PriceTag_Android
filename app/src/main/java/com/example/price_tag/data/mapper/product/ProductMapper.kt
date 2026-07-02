package com.example.price_tag.data.mapper.product

import com.example.price_tag.data.mapper.common.toDomain
import com.example.price_tag.data.model.product.ProductDataDto
import com.example.price_tag.data.model.product.ProductResponseDto
import com.example.price_tag.domain.model.common.PaginationModel
import com.example.price_tag.domain.model.product.ProductData
import com.example.price_tag.domain.model.product.Products

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
        description = description,
        shortDescription = shortDescription,
        imageUrl = primaryImageURL,
        basePrice = basePrice ?: 0.0,
        discountedPrice = discountedPrice ?: basePrice ?: 0.0,
        brand = brand,
        rating = totalRating ?: 0.0,
        totalReview = totalReview ?: 0L,
    )
}