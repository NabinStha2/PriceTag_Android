package com.example.price_tag.data.model.product_details

import com.example.price_tag.data.model.product.Image
import com.example.price_tag.data.model.product.ProductVariant

data class ProductDetailsResponse(
    val message: String? = null,
    val data: ProductDetailsData? = null,
    val success: Boolean? = null
)

data class ProductDetailsData(
    val results: List<ProductDetailsResult>? = null,
)

data class ProductDetailsResult(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val images: List<Image>? = null,
    val variants: List<ProductVariant>? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
