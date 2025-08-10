package com.example.price_tag.data.model.category

data class SubCategoryResponse (
    val message: String?,
    val data: SubCategoryData?,
    val success: Boolean?
)

data class SubCategoryData (
    val results: List<SubCategoryResult>?,
    val categoryName: String?
)

data class SubCategoryResult (
    val id: Long?,
    val subCategoryName: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val categoryId: Long?
)
