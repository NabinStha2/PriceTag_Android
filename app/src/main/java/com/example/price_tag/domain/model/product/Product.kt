package com.example.price_tag.domain.model.product

data class Product(
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

data class ProductList(
    val products: List<Product>,
    val currentPage: Int,
    val totalPages: Int,
    val totalItems: Long,
    val hasNext: Boolean,
)
