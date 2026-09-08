package com.example.price_tag.feature.checkout.presentation.model

data class AddressFormState(
    val unitNumber: String = "",
    val streetNumber: String = "",
    val street: String = "",
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val postalCode: String = "",
    val label: String = "",
    val isDefault: Boolean = false,
)
