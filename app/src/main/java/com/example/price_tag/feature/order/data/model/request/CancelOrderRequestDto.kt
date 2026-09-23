package com.example.price_tag.feature.order.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CancelOrderRequestDto(
    val reason: String,
)