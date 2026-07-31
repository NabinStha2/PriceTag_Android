package com.example.price_tag.feature.category.domain.model

data class Category(
    val id: Long,
    val name: String,
    val imageUrl: String? = null,
)

data class SubCategory(
    val id: Long,
    val name: String,
    val categoryId: Long,
    val imageUrl: String? = null,
)
