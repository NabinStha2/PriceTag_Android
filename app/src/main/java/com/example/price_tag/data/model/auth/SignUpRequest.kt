package com.example.price_tag.data.model.auth

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
)
