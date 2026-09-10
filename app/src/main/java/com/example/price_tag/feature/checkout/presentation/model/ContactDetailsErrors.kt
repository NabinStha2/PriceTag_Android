package com.example.price_tag.feature.checkout.presentation.model

data class ContactDetailsErrors(
    val recipientName: String? = null,
    val phoneNumber: String? = null,
) {
    val isValid: Boolean
        get() = listOf(recipientName,
                       phoneNumber).all { it == null }

}