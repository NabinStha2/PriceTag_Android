package com.example.price_tag.feature.checkout.domain.usecase

import com.example.price_tag.feature.checkout.domain.model.PaymentIntentDetails
import com.example.price_tag.feature.checkout.domain.repository.CheckoutRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class InitializePaymentUseCase @Inject constructor(private val checkoutRepository: CheckoutRepository) {
    operator fun invoke(orderNumber: String): Flow<ResourceState<PaymentIntentDetails>> {
        return checkoutRepository.initializePayment(orderNumber)
    }

}