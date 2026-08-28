package com.example.price_tag.feature.checkout.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderResponseDto(
    val orderId: Long,
    val orderNumber: String,
    val status: String,
    val totalAmount: Double,
    val currency: String,
)