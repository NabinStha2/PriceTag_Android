package com.example.price_tag.core.common.domain.model

data class CommonResponse(
    val message: String?,
    val success: Boolean?,
    val statusCode: Int? = null,
)
