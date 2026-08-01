package com.example.price_tag.feature.cart.domain.usecase

import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.price_tag.feature.cart.domain.repository.CartRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCartItemsUseCase @Inject constructor(private val repository: CartRepository) {
    suspend operator fun invoke(): Flow<ResourceState<Cart>> {
        return repository.getAllCartItems()
    }
}
