package com.example.price_tag.data.model.category

data class CategoryResponse(
    val message: String?,
    val data: List<CategoryData>?,
    val success: Boolean?,
    val status: Int?,
    //    val meta: Object?,
)

data class CategoryData(
    val id: Long?,
    val name: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val imageUrl: String?,
)
