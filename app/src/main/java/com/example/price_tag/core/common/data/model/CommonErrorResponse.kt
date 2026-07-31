package com.example.price_tag.core.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CommonErrorResponse(
    val message: String,
    val statusCode: Int? = null,
)