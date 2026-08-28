package com.example.price_tag.feature.checkout.domain.model

data class PaymentIntentDetails(
    val clientSecret: String,
    val paymentIntentId: String,
    val orderNumber: String,
    val amountInMinorUnits: Long,
    val currency: String,
    val expiresAtIso: String?,
)