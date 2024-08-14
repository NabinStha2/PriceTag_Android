package com.example.price_tag.data.model.auth

data class CommonErrorResponse(
    val message: String,
    val statusCode: Int? = null,
)
