package com.example.price_tag.data.model.product

data class ProductResponse (
    val message: String? = null,
    val data: ProductData? = null,
    val success: Boolean? = null
)

data class ProductData (
    val results: List<ProductResult>? = null,
    val pagination: Pagination? = null
)

data class Pagination (
    val totalItems: Long? = null,
    val totalPages: Long? = null,
    val hasPrevious: Boolean? = null,
    val currentPage: Long? = null,
    val hasNext: Boolean? = null,
    val itemsPerPage: Long? = null
)

data class ProductResult (
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val images: List<Image?>? = null,
    val variants: List<ProductVariant>? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class Image (
    val id: Long,
    val url: String,
    val publicId: String
)

data class ProductVariant (
    val id: Long? = null,
    val size: List<String>? = null,
    val color: String? = null,
    val quantity: Long? = null,
    val actualPrice: Long? = null,
    val discountedPrice: Long? = null
)
