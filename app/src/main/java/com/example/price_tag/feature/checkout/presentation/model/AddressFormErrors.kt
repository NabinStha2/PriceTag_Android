package com.example.price_tag.feature.checkout.presentation.model

data class AddressFormErrors(
    val unitNumber: String? = null,
    val streetNumber: String? = null,
    val street: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val postalCode: String? = null,
    val label: String? = null,
) {
    val isValid: Boolean
        get() = listOf(
            unitNumber,
            streetNumber,
            street,
            city,
            state,
            country,
            postalCode,
            label,
        ).all { it == null }
}