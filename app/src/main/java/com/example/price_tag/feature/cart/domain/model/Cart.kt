package com.example.price_tag.feature.cart.domain.model

data class Cart(
    val cartId: Long,
    val status: String,
    val items: List<CartItem>,
    val cartTotal: Double,
    val totalItemCount: Long,
)

data class CartItem(
    val itemId: Long,
    val variantId: Long,
    val sku: String,
    val productName: String,
    val imageUrl: String?,
    val colorName: String?,
    val colorHex: String?,
    val sizeName: String?,
    val unitPrice: Double,
    val quantity: Long,
    val lineTotal: Double,
    val stockAvailable: Long,
)