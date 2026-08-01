package com.example.price_tag.feature.cart.data.mapper

import com.example.price_tag.feature.cart.data.model.CartDataDto
import com.example.price_tag.feature.cart.data.model.CartItemDto
import com.example.price_tag.feature.cart.data.model.CartResponseDto
import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.price_tag.feature.cart.domain.model.CartItem

fun CartResponseDto.toDomain(): Cart {
    return data?.toDomain() ?: Cart(
        cartId = 0,
        status = "",
        items = emptyList(),
        cartTotal = 0.0,
        totalItemCount = 0,
    )
}

fun CartDataDto.toDomain(): Cart {
    return Cart(
        cartId = cartID ?: 0,
        status = status ?: "",
        items = items?.map { it.toDomain() } ?: emptyList(),
        cartTotal = cartTotal ?: 0.0,
        totalItemCount = totalItemCount ?: 0,
    )
}

fun CartItemDto.toDomain(): CartItem {
    return CartItem(
        itemId = itemID ?: 0,
        variantId = variantID ?: 0,
        sku = sku ?: "",
        productName = productName ?: "",
        imageUrl = imageURL ?: "",
        colorName = colorName ?: "",
        colorHex = colorHex ?: "",
        sizeName = sizeName ?: "",
        unitPrice = unitPrice ?: 0.0,
        quantity = quantity ?: 0,
        lineTotal = lineTotal ?: 0.0,
        stockAvailable = stockAvailable ?: 0,
    )
}
