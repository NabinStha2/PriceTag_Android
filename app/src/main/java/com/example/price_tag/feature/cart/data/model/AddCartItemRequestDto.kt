package com.example.price_tag.feature.cart.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AddCartItemRequestDto(
    val variantId: Long,
    val quantity: Long,
)
