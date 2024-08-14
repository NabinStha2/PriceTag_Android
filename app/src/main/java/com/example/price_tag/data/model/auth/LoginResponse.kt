package com.example.price_tag.data.model.auth


data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData?,
)


data class LoginData(
    val accessToken: String?,
    val refreshToken: String?,
)