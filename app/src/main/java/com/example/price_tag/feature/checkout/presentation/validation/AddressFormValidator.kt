package com.example.price_tag.feature.checkout.presentation.validation

import com.example.price_tag.feature.checkout.presentation.model.AddressFormErrors
import com.example.price_tag.feature.checkout.presentation.model.AddressFormState


fun AddressFormState.validate(): AddressFormErrors {
    return AddressFormErrors(
        unitNumber = unitNumber.fieldError(
            fieldName = "Unit Number",
            maxLength = 50,
            required = false,
        ),
        streetNumber = streetNumber.fieldError(
            fieldName = "Street number",
            maxLength = 50,
            required = false,
        ),
        street = street.fieldError(
            fieldName = "Street",
            maxLength = 150,
        ),
        city = city.fieldError(
            fieldName = "City",
            maxLength = 100,
        ),
        state = state.fieldError(
            fieldName = "State",
            maxLength = 100,
        ),
        country = country.fieldError(
            fieldName = "Country",
            maxLength = 100,
        ),
        postalCode = postalCode.fieldError(
            fieldName = "Postal code",
            maxLength = 20,
        ),
        label = label.fieldError(
            fieldName = "Label",
            maxLength = 50,
            required = false,
        ),
    )
}

private fun String.fieldError(
    fieldName: String,
    maxLength: Int,
    required: Boolean = true,
): String? {
    val value = trim()

    return when {
        required && value.isBlank() -> "$fieldName is required"
        value.length > maxLength -> "$fieldName must be at most $maxLength characters long"
        else -> null
    }
}