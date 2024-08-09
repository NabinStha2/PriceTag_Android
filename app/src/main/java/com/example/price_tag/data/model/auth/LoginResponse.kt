package com.example.price_tag.data.model.auth

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)
