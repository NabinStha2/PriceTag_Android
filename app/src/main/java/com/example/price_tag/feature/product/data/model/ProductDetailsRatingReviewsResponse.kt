package com.example.price_tag.feature.product.data.model

// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json                                         = Json { allowStructuredMapKeys = true }
// val singleProductDetailsRatingReviewsResponseDto = json.parse(SingleProductDetailsRatingReviewsResponseDto.serializer(), jsonString)


import com.example.price_tag.core.common.data.model.CommonPaginationResponseDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailsRatingReviewsResponseDto(
    val message: String? = null,
    val data: List<ProductDetailsRatingReviewsDataDto>? = null,
    val success: Boolean? = null,
    val status: Long? = null,
    val pagination: CommonPaginationResponseDto? = null,
)

@Serializable
data class ProductDetailsRatingReviewsDataDto(
    val id: Long? = null,

    @SerialName("userId") val userID: Long? = null,

    @SerialName("productId") val productID: Long? = null,

    val rating: Double? = null,
    val review: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val userFullName: String? = null,
    val productName: String? = null,
)
