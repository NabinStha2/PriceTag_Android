package com.example.price_tag.feature.cart.domain.repository

import com.example.price_tag.core.common.domain.model.CommonResponse
import com.example.price_tag.feature.cart.data.model.AddCartItemRequestDto
import com.example.price_tag.feature.cart.data.model.UpdateCartItemQuantityRequestDto
import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getAllCartItems(): Flow<ResourceState<Cart>>

    suspend fun addItemToCart(addCartItemRequestDto: AddCartItemRequestDto): Flow<ResourceState<CommonResponse>>

    suspend fun removeCartItem(cartItemId: Long): Flow<ResourceState<CommonResponse>>

    suspend fun updateCartItemQuantity(updateCartItemQuantityRequestDto: UpdateCartItemQuantityRequestDto): Flow<ResourceState<CommonResponse>>
}
