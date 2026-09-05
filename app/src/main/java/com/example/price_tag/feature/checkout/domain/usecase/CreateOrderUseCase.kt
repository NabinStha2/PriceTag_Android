package com.example.price_tag.feature.checkout.domain.usecase

import com.example.price_tag.feature.checkout.domain.model.CreateOrderParams
import com.example.price_tag.feature.checkout.domain.model.CreatedOrder
import com.example.price_tag.feature.checkout.domain.repository.CheckoutRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CreateOrderUseCase @Inject constructor(private val checkoutRepository: CheckoutRepository) {
    operator fun invoke(createOrderParams: CreateOrderParams): Flow<ResourceState<CreatedOrder>> {
        return checkoutRepository.createOrder(createOrderParams)
    }

}