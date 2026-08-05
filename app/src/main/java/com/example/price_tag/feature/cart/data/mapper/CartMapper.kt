package com.example.price_tag.feature.cart.data.mapper

import com.example.price_tag.feature.cart.data.model.CartDataDto
import com.example.price_tag.feature.cart.data.model.CartItemDto
import com.example.price_tag.feature.cart.data.model.CartResponseDto
import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.price_tag.feature.cart.domain.model.CartItem


fun CartItemDto.toDomain(): CartItem? {
    val id = itemID ?: return null
    val variantId = variantID ?: return null
    val skuValue = sku ?: return null
    val name = productName ?: return null
    val price = unitPrice ?: return null
    val qty = quantity ?: return null
    val total = lineTotal ?: return null

    return CartItem(
        itemId = id,
        variantId = variantId,
        sku = skuValue,
        productName = name,
        imageUrl = imageURL,       // legitimately optional — pass through
        colorName = colorName,
        colorHex = colorHex,
        sizeName = sizeName,
        unitPrice = price,
        quantity = qty,
        lineTotal = total,
        stockAvailable = stockAvailable ?: 0,
    )
}

fun CartDataDto.toDomain(): Cart? {
    val id = cartID ?: return null
    return Cart(
        cartId = id,
        status = status.orEmpty(),
        items = items.orEmpty()
            .mapNotNull { it.toDomain() },
        cartTotal = cartTotal ?: 0.0,
        totalItemCount = totalItemCount ?: 0,
    )
}

fun CartResponseDto.toDomain(): Cart? = data?.toDomain()