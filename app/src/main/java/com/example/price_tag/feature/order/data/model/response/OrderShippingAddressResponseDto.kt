package com.example.price_tag.feature.order.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderShippingAddressResponseDto(
    val recipientName: String,
    val phone: String,
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val sourceAddressId: Long? = null,
    val unitNumber: String? = null,
    val streetNumber: String? = null,
)
