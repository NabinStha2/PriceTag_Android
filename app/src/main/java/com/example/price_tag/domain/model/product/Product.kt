package com.example.price_tag.domain.model.product

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String?,
    val price: Long,
    val discountedPrice: Long?
)

data class ProductList(
    val products: List<Product>,
    val totalItems: Long,
    val totalPages: Long,
    val currentPage: Long
)
