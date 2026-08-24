package com.example.price_tag.feature.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNumber: Long,
)
