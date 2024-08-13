package com.example.price_tag.data.model.auth

data class SignUpRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNumber: Long,
)
