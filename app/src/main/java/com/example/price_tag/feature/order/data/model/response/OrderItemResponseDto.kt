package com.example.price_tag.feature.order.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemResponseDto(
    val itemId: Long,
    val variantId: Long,
    val sku: String,
    val productName: String,
    val quantity: Int,
    val unitPrice: Double,
    val totalAmount: Double,
    val imageUrl: String? = null,
    val colorName: String? = null,
    val sizeName: String? = null,
)
