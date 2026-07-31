package com.example.price_tag.feature.category.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SubCategoryResponse(
    val message: String?,
    val data: SubCategoryData?,
    val success: Boolean?,
)

@Serializable
data class SubCategoryData(
    val results: List<SubCategoryResult>?,
    val categoryName: String?,
)

@Serializable
data class SubCategoryResult(
    val id: Long?,
    val subCategoryName: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val categoryId: Long?,
    val imageUrl: String? = null,
)
