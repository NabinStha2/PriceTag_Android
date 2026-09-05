package com.example.price_tag.feature.checkout.domain.usecase

import com.example.price_tag.feature.checkout.domain.model.Address
import com.example.price_tag.feature.checkout.domain.model.NewAddress
import com.example.price_tag.feature.checkout.domain.repository.CheckoutRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CreateAddressUseCase @Inject constructor(private val checkoutRepository: CheckoutRepository) {
    operator fun invoke(newAddress: NewAddress): Flow<ResourceState<List<Address>>> {
        return checkoutRepository.createAddress(newAddress)
    }

}