package com.example.price_tag.feature.checkout.presentation.mapper

import com.example.price_tag.feature.checkout.domain.model.NewAddress
import com.example.price_tag.feature.checkout.presentation.model.AddressFormState


fun AddressFormState.isValid(): Boolean {
    return listOf(
        street,
        city,
        state,
        country,
        postalCode,
    ).all { it.isNotBlank() }
}

fun AddressFormState.toNewAddress(): NewAddress {
    return NewAddress(
        unitNumber = unitNumber.trim()
            .ifEmpty { null },
        streetNumber = streetNumber.trim()
            .ifEmpty { null },
        street = street.trim(),
        city = city.trim(),
        state = state.trim(),
        country = country.trim(),
        postalCode = postalCode.trim(),
        label = label.trim()
            .ifEmpty { null },
        isDefault = isDefault,
    )
}