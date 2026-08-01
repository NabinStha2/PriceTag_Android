package com.example.price_tag.feature.cart.data.remote

import com.example.price_tag.core.network.ApiService
import com.example.price_tag.feature.cart.data.model.CartResponseDto
import retrofit2.Response
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor(private val apiService: ApiService) : CartDataSource {

    override suspend fun getAllCartItems(): Response<CartResponseDto> {
        return apiService.getAllCartItems()
    }
}