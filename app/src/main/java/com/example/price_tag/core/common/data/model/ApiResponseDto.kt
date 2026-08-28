package com.example.price_tag.core.common.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement


@Serializable
data class ApiResponseDto<T>(
    val message: String? = null,
    val data: T? = null,
    val success: Boolean = false,
    val status: Int? = null,
    val pagination: CommonPaginationResponseDto? = null,
    val meta: Map<String, JsonElement>? = null,
)