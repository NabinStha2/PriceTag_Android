package com.example.price_tag.data.model.common

import kotlinx.serialization.Serializable

@Serializable
data class CommonErrorResponse(
    val message: String,
    val statusCode: Int? = null,
)