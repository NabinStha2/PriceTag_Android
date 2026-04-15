package com.example.price_tag.domain.model.category

data class Category(
    val id: Long,
    val name: String
)

data class SubCategory(
    val id: Long,
    val name: String,
    val categoryId: Long
)
