package com.example.price_tag.feature.checkout.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderRequestDto(
    val addressId: Long,
    val recipientName: String,
    val phoneNumber: String,
)
