package com.example.price_tag.feature.checkout.domain.model

data class Address(
    val id: Long,
    val unitNumber: String?,
    val streetNumber: String?,
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val label: String?,
    val isDefault: Boolean,
)

data class NewAddress(
    val unitNumber: String?,
    val streetNumber: String?,
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val label: String?,
    val isDefault: Boolean,
)
