package com.example.price_tag.data.model

data class CommonErrorResponse(
    val message: String,
    val statusCode: Int? = null,
)
