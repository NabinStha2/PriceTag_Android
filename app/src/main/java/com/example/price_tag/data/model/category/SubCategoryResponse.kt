package com.example.price_tag.data.model.category

import com.squareup.moshi.Json


data class SubCategoryResponse(
    val message: String?, val data: SubCategoryResponseData?, val success: Boolean?
)

data class SubCategoryResponseData(
    val results: List<SubCategoryResult>?, val categoryName: String?
)

data class SubCategoryResult(
    val id: Long?, val subCategoryName: String?, val createdAt: String?, val updatedAt: String?,

    @Json(name = "categoryId") val categoryID: Long?
)
