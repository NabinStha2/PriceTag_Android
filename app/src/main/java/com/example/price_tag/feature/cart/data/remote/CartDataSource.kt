package com.example.price_tag.feature.cart.data.remote

import com.example.price_tag.feature.cart.data.model.CartResponseDto
import retrofit2.Response

interface CartDataSource {

    suspend fun getAllCartItems(): Response<CartResponseDto>

}