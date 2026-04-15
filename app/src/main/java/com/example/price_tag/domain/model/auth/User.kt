package com.example.price_tag.domain.model.auth

data class User(
    val accessToken: String,
    val refreshToken: String
)
