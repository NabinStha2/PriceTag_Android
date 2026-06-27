package com.example.price_tag.data.model.auth

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