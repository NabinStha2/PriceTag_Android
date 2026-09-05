package com.example.price_tag.feature.checkout.domain.repository

import com.example.price_tag.feature.checkout.domain.model.Address
import com.example.price_tag.feature.checkout.domain.model.CreateOrderParams
import com.example.price_tag.feature.checkout.domain.model.CreatedOrder
import com.example.price_tag.feature.checkout.domain.model.NewAddress
import com.example.price_tag.feature.checkout.domain.model.PaymentIntentDetails
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow

interface CheckoutRepository {

    fun getAddresses(): Flow<ResourceState<List<Address>>>

    fun createAddress(newAddress: NewAddress): Flow<ResourceState<List<Address>>>

    fun createOrder(createOrderParams: CreateOrderParams): Flow<ResourceState<CreatedOrder>>

    fun initializePayment(orderNumber: String): Flow<ResourceState<PaymentIntentDetails>>

}