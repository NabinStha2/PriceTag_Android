package com.example.price_tag.feature.checkout.data.remote

import com.example.price_tag.core.common.data.model.ApiResponseDto
import com.example.price_tag.core.network.ApiService
import com.example.price_tag.feature.checkout.data.model.request.CreateAddressRequestDto
import com.example.price_tag.feature.checkout.data.model.request.CreateOrderRequestDto
import com.example.price_tag.feature.checkout.data.model.response.AddressResponseDto
import com.example.price_tag.feature.checkout.data.model.response.CreateOrderResponseDto
import com.example.price_tag.feature.checkout.data.model.response.PaymentIntentResponseDto
import retrofit2.Response
import javax.inject.Inject

class CheckoutDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    CheckoutDataSource {

    override suspend fun getAddresses(): Response<ApiResponseDto<List<AddressResponseDto>>> {
        return apiService.getAddresses()
    }

    override suspend fun createAddress(createAddressRequestDto: CreateAddressRequestDto): Response<ApiResponseDto<List<AddressResponseDto>>> {
        return apiService.createAddress(createAddressRequestDto)
    }

    override suspend fun createOrder(createOrderRequestDto: CreateOrderRequestDto): Response<ApiResponseDto<CreateOrderResponseDto>> {
        return apiService.createOrder(createOrderRequestDto)
    }

    override suspend fun initializePayment(orderNumber: String): Response<ApiResponseDto<PaymentIntentResponseDto>> {
        return apiService.initializePayment(orderNumber)
    }
}