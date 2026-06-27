package com.example.price_tag.data.model.product

import com.example.price_tag.core.common.CommonPaginationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(val message: String? = null,
                           val data: List<ProductData>? = null,
                           val success: Boolean? = null,
                           val status: Long? = null,
                           val pagination: CommonPaginationModel? = null)

@Serializable
data class ProductData(val id: Long? = null,
                       val name: String? = null,
                       val slug: String? = null,
                       val description: String? = null,
                       val shortDescription: String? = null,

                       @SerialName("primaryImageUrl") val primaryImageURL: String? = null,

                       val basePrice: Long? = null,
                       val discountedPrice: Double? = null,
                       val brand: String? = null,
                       val totalRating: Double? = null,
                       val totalReview: Long? = null,
                       val createdAt: String? = null,
                       val updatedAt: String? = null,
                       val category: Category? = null,
                       val subCategory: SubCategory? = null)

@Serializable
data class Category(val id: Long? = null,
                    val name: String? = null,

                    @SerialName("imageUrl") val imageURL: String? = null,

                    val createdAt: String? = null,
                    val updatedAt: String? = null)

@Serializable
data class SubCategory(val id: Long? = null,
                       val subCategoryName: String? = null,
                       val createdAt: String? = null,
                       val updatedAt: String? = null,

                       @SerialName("categoryId") val categoryID: Long? = null)

