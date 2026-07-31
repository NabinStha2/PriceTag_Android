package com.example.price_tag.feature.auth.data.model

data class OtpRequest(
    val email: String,
    val otpCode: Int,
    val newPassword: String,
)
