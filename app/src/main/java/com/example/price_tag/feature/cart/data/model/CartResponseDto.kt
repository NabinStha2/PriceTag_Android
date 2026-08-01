// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json         = Json { allowStructuredMapKeys = true }
// val cartResponse = json.parse(CartResponse.serializer(), jsonString)

package com.example.price_tag.feature.cart.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartResponseDto(
    val message: String? = null,
    val data: CartDataDto? = null,
    val success: Boolean? = null,
    val status: Long? = null,
)

@Serializable
data class CartDataDto(
    @SerialName("cartId") val cartID: Long? = null,

    val status: String? = null,
    val items: List<CartItemDto>? = null,
    val cartTotal: Double? = null,
    val totalItemCount: Long? = null,
)

@Serializable
data class CartItemDto(
    @SerialName("itemId") val itemID: Long? = null,

    @SerialName("variantId") val variantID: Long? = null,

    val sku: String? = null,
    val productName: String? = null,

    @SerialName("imageUrl") val imageURL: String? = null,

    val colorName: String? = null,
    val colorHex: String? = null,
    val sizeName: String? = null,
    val unitPrice: Double? = null,
    val quantity: Long? = null,
    val lineTotal: Double? = null,
    val stockAvailable: Long? = null,
)
