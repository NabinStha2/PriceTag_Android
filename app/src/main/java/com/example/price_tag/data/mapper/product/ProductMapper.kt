package com.example.price_tag.data.mapper.product

import com.example.price_tag.data.model.product.ProductResponse
import com.example.price_tag.data.model.product.ProductResult
import com.example.price_tag.data.model.product_details.ProductDetailsResponse
import com.example.price_tag.domain.model.product.Product
import com.example.price_tag.domain.model.product.ProductList

fun ProductResult.toDomain(): Product {
    val variant = variants?.firstOrNull()
    return Product(
        id = id ?: 0L,
        name = name ?: "",
        description = description ?: "",
        imageUrl = images?.firstOrNull()?.url,
        price = variant?.actualPrice ?: 0L,
        discountedPrice = variant?.discountedPrice
    )
}

fun ProductResponse.toDomain(): ProductList {
    return ProductList(
        products = data?.results?.map { it.toDomain() } ?: emptyList(),
        totalItems = data?.pagination?.totalItems ?: 0L,
        totalPages = data?.pagination?.totalPages ?: 0L,
        currentPage = data?.pagination?.currentPage ?: 0L
    )
}

fun ProductDetailsResponse.toDomain(): Product {
    val result = data?.results
    val variant = result?.variants?.firstOrNull()
    return Product(
        id = result?.id ?: 0L,
        name = result?.name ?: "",
        description = result?.description ?: "",
        imageUrl = result?.images?.firstOrNull()?.url,
        price = variant?.actualPrice ?: 0L,
        discountedPrice = variant?.discountedPrice
    )
}
