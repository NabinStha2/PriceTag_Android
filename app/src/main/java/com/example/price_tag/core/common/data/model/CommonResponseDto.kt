package com.example.price_tag.core.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CommonResponseDto(
    val message: String,
    val success: Boolean,
    val statusCode: Int? = null,
)