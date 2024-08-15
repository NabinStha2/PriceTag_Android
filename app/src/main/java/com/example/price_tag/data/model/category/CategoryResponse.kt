package com.example.price_tag.data.model.category

data class CategoryResponse(
    val message: String,
    val data: CategoryData,
    val success: Boolean
)

data class CategoryData(
    val results: List<CategoryResult>
)

data class CategoryResult(
    val id: Long,
    val categoryName: String,
    val createdAt: String,
    val updatedAt: String
)
