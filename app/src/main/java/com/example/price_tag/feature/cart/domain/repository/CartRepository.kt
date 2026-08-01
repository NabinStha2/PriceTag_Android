package com.example.price_tag.feature.cart.domain.repository

import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getAllCartItems(): Flow<ResourceState<Cart>>
}
