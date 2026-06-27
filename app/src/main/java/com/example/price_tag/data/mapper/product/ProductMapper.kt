package com.example.price_tag.data.mapper.product

import com.example.price_tag.data.model.product.ProductData
import com.example.price_tag.data.model.product.ProductResponse
import com.example.price_tag.domain.model.product.Product
import com.example.price_tag.domain.model.product.ProductList

fun ProductResponse.toDomain(): ProductList {
    return ProductList(
        products = data?.map { it.toDomain() } ?: emptyList(),
        currentPage = pagination?.currentPage?.toInt() ?: 1,
        totalPages = pagination?.totalPages?.toInt() ?: 1,
        totalItems = pagination?.totalItems ?: 0,
        hasNext = pagination?.hasNext ?: false,
    )
}

fun ProductData.toDomain(): Product {
    return Product(
        id = id ?: 0L,
        name = name.orEmpty(),
        slug = slug.orEmpty(),
        description = description,
        shortDescription = shortDescription,
        imageUrl = primaryImageURL,
        basePrice = basePrice?.toDouble() ?: 0.0,
        discountedPrice = discountedPrice ?: basePrice?.toDouble() ?: 0.0,
        brand = brand,
        rating = totalRating ?: 0.0,
        totalReview = totalReview ?: 0L,
    )
}