package com.example.price_tag.feature.product.data.model

import com.example.price_tag.core.common.data.model.CommonPaginationResponseDto
import com.example.price_tag.feature.category.data.model.CategoryData
import com.example.price_tag.feature.category.data.model.SubCategoryResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDto(
    val message: String? = null,
    val data: List<ProductDataDto>? = null,
    val success: Boolean? = null,
    val status: Long? = null,
    val pagination: CommonPaginationResponseDto? = null,
)

@Serializable
data class ProductDataDto(
    val id: Long? = null,
    val name: String? = null,
    val slug: String? = null,
    val description: String? = null,
    val shortDescription: String? = null,
    @SerialName("primaryImageUrl") val primaryImageURL: String? = null,
    val basePrice: Double? = null,
    val discountedPrice: Double? = null,
    val brand: String? = null,
    val totalRating: Double? = null,
    val totalReview: Long? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val categoryData: CategoryData? = null,
    val subCategoryResult: SubCategoryResult? = null,
)