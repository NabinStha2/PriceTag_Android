package com.example.price_tag.feature.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class OtpRequest(
    val email: String,
    val otpCode: Int,
    val newPassword: String,
)
