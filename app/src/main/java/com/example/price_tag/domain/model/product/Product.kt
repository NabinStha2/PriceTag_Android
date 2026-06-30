package com.example.price_tag.domain.model.product

import com.example.price_tag.domain.model.common.PaginationModel

data class Products(
    val products: List<ProductData>,
    val pagination: PaginationModel,
)

data class ProductData(
    val id: Long,
    val name: String,
    val slug: String,
    val description: String?,
    val shortDescription: String?,
    val imageUrl: String?,
    val basePrice: Double,
    val discountedPrice: Double,
    val brand: String?,
    val rating: Double,
    val totalReview: Long,
)


