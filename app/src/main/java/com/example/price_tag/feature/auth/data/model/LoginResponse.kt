package com.example.price_tag.feature.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData?,
)

@Serializable
data class LoginData(
    val accessToken: String?,
    val refreshToken: String?,
)
