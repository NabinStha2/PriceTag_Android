package com.example.price_tag.feature.checkout.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class PaymentIntentResponseDto(
    val clientSecret: String,
    val paymentIntentId: String,
    val orderNumber: String,
    val amount: Long,
    val currency: String,
    val expiresAt: String? = null,
)
