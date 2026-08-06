package com.example.price_tag.feature.cart.data.remote

import com.example.price_tag.core.common.data.model.CommonResponseDto
import com.example.price_tag.feature.cart.data.model.AddCartItemRequestDto
import com.example.price_tag.feature.cart.data.model.CartResponseDto
import com.example.price_tag.feature.cart.data.model.UpdateCartItemQuantityRequestDto
import retrofit2.Response

interface CartDataSource {

    suspend fun getAllCartItems(): Response<CartResponseDto>

    suspend fun addItemToCart(addCartItemRequestDto: AddCartItemRequestDto): Response<CommonResponseDto>

    suspend fun removeCartItem(cartItemId: Long): Response<CommonResponseDto>

    suspend fun updateCartItemQuantity(updateCartItemQuantityRequestDto: UpdateCartItemQuantityRequestDto): Response<CommonResponseDto>

}