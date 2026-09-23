package com.example.price_tag.feature.order.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderSummaryResponseDto(
    val orderId: Long,
    val orderNumber: String,
    val status: String,
    val paymentStatus: String,
    val totalAmount: Double,
    val currency: String,
    val trackingNumber: String? = null,
    val createdAt: String? = null,
    val expiresAt: String? = null,
)
