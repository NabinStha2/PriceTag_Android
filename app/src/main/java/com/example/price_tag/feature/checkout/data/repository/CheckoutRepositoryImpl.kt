package com.example.price_tag.feature.checkout.data.repository

import com.example.price_tag.feature.checkout.domain.model.Address
import com.example.price_tag.feature.checkout.domain.model.CreateOrderParams
import com.example.price_tag.feature.checkout.domain.model.CreatedOrder
import com.example.price_tag.feature.checkout.domain.model.NewAddress
import com.example.price_tag.feature.checkout.domain.model.PaymentIntentDetails
import com.example.price_tag.feature.checkout.domain.repository.CheckoutRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckoutRepositoryImpl @Inject constructor() : CheckoutRepository {
    override fun getAddresses(): Flow<ResourceState<List<Address>>> =
        flow<ResourceState<List<Address>>> {
            emit(ResourceState.Loading)
        }.catch { e ->
            emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
        }

    override fun createAddress(newAddress: NewAddress): Flow<ResourceState<Address>> {
        TODO("Not yet implemented")
    }

    override fun createOrder(createOrderParams: CreateOrderParams): Flow<ResourceState<CreatedOrder>> {
        TODO("Not yet implemented")
    }

    override fun initializePayment(orderNumber: String): Flow<ResourceState<PaymentIntentDetails>> {
        TODO("Not yet implemented")
    }
}