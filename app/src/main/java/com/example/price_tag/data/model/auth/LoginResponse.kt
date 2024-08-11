package com.example.price_tag.data.model.auth


data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: Map<String, Any>,
)
