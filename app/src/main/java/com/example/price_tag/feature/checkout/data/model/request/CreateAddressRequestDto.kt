package com.example.price_tag.feature.checkout.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateAddressRequestDto(
    val unitNumber: String? = null,
    val streetNumber: String? = null,
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val label: String? = null,
    val isDefault: Boolean? = null,
)