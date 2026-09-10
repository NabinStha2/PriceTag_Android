package com.example.price_tag.feature.checkout.presentation.validation

import com.example.price_tag.feature.checkout.presentation.model.ContactDetailsErrors


fun validateCheckoutContactDetails(
    recipientName: String,
    phoneNumber: String,
): ContactDetailsErrors {
    val name = recipientName.trim()
    val phone = phoneNumber.trim()

    return ContactDetailsErrors(
        recipientName = when {
            name.isBlank() -> "Recipient name cannot be empty"
            name.length > 100 -> "Recipient name cannot be longer than 100 characters"
            else -> null
        },
        phoneNumber = when {
            phone.isBlank() -> "Phone number cannot be empty"
            phone.length > 30 -> "Phone number cannot be longer than 30 characters"
            else -> null
        },
    )
}