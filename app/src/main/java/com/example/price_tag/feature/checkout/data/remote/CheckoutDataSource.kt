package com.example.price_tag.feature.checkout.data.remote

import com.example.price_tag.core.common.data.model.ApiResponseDto
import com.example.price_tag.feature.checkout.data.model.request.CreateAddressRequestDto
import com.example.price_tag.feature.checkout.data.model.request.CreateOrderRequestDto
import com.example.price_tag.feature.checkout.data.model.response.AddressResponseDto
import com.example.price_tag.feature.checkout.data.model.response.CreateOrderResponseDto
import com.example.price_tag.feature.checkout.data.model.response.PaymentIntentResponseDto
import retrofit2.Response

interface CheckoutDataSource {
    suspend fun getAddresses(): Response<ApiResponseDto<List<AddressResponseDto>>>

    suspend fun createAddress(
        createAddressRequestDto: CreateAddressRequestDto,
    ): Response<ApiResponseDto<List<AddressResponseDto>>>

    suspend fun createOrder(
        createOrderRequestDto: CreateOrderRequestDto,
    ): Response<ApiResponseDto<CreateOrderResponseDto>>

    suspend fun initializePayment(
        orderNumber: String,
    ): Response<ApiResponseDto<PaymentIntentResponseDto>>
}