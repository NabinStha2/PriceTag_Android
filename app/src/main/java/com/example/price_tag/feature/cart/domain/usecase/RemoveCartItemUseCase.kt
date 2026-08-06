package com.example.price_tag.feature.cart.domain.usecase

import com.example.price_tag.core.common.domain.model.CommonResponse
import com.example.price_tag.feature.cart.domain.repository.CartRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveCartItemUseCase @Inject constructor(private val repository: CartRepository) {
    suspend operator fun invoke(cartItemId: Long): Flow<ResourceState<CommonResponse>> {
        return repository.removeCartItem(cartItemId = cartItemId)
    }
}
