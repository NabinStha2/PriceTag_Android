package com.example.price_tag.feature.auth.domain.model

data class User(
    val accessToken: String,
    val refreshToken: String
)
