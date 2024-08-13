package com.example.price_tag.data.model.auth

data class OtpRequest(
    val email: String,
    val otpCode: Int,
    val newPassword: String,
)