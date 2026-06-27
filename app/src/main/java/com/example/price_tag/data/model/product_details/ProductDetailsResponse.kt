package com.example.price_tag.data.model.product_details



import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailsResponse(
    val message: String? = null,
    val data: ProductDetailsData? = null,
    val success: Boolean? = null
)

@Serializable
data class ProductDetailsData(
    val results: ProductDetailsResult? = null,
)

@Serializable
data class ProductDetailsResult(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val images: List<Image>? = null,
    val variants: List<ProductVariant>? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)


@Serializable
data class Image(val id: Long? = null, val url: String? = null)


@Serializable
data class ProductVariant(
    val id: Long? = null,
    val color: String? = null,
    val size: List<String>? = null,
    val quantity: Int? = null,
)

