package com.example.price_tag.feature.cart.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCartItemQuantityRequestDto(
    val cartItemId: Long,
    val quantity: Int,
)
