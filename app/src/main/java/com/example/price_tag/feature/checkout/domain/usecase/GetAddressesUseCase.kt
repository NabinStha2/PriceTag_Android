package com.example.price_tag.feature.checkout.domain.usecase

import com.example.price_tag.feature.checkout.domain.model.Address
import com.example.price_tag.feature.checkout.domain.repository.CheckoutRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(private val checkoutRepository: CheckoutRepository) {
    operator fun invoke(): Flow<ResourceState<List<Address>>> {
        return checkoutRepository.getAddresses()
    }
}