package com.example.price_tag.feature.checkout.data.repository

import com.example.price_tag.core.common.data.model.ApiResponseDto
import com.example.price_tag.core.common.data.model.CommonErrorResponse
import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.core.utils.JsonProvider.json
import com.example.price_tag.feature.checkout.data.mapper.toDomain
import com.example.price_tag.feature.checkout.data.model.response.AddressResponseDto
import com.example.price_tag.feature.checkout.data.remote.CheckoutDataSource
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
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class CheckoutRepositoryImpl @Inject constructor(
    private val checkoutDataSource: CheckoutDataSource,
    private val dataStoreManager: DataStoreManager,
) : CheckoutRepository {
    override fun getAddresses(): Flow<ResourceState<List<Address>>> =
        flow<ResourceState<List<Address>>> {
            emit(ResourceState.Loading)
            val response: Response<ApiResponseDto<List<AddressResponseDto>>> =
                checkoutDataSource.getAddresses()
            val body = response.body()
            if (response.isSuccessful && body != null && body.success) {
                val data = body.data
                if (data == null) {
                    emit(ResourceState.Error("Failed to load addresses"))
                    return@flow
                }
                emit(ResourceState.Success(data.map { it.toDomain() }))
            } else {
                handleUnauthorized(response.code())
                val message = parseError(
                    response.errorBody()
                        ?.string(),
                ) ?: body?.message ?: "Failed to load addresses"

                emit(ResourceState.Error(message))
            }
        }.catch { throwable ->
            if (throwable is CancellationException) {
                throw throwable
            }
            emit(
                ResourceState.Error(
                    throwable.toUserMessage(),
                ),
            )
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


    private suspend fun handleUnauthorized(statusCode: Int) {
        if (statusCode != 401) return
        try {
            dataStoreManager.clearAuthTokens()
        } catch (_: Exception) { // The original API error should still be returned.
        }
    }

    private fun parseError(errorBody: String?): String? = errorBody?.let {
        try {
            json.decodeFromString<CommonErrorResponse>(it).message
        } catch (_: Exception) {
            null
        }
    }

    private fun Throwable.toUserMessage(): String {
        return when (this) {
            is IOException -> "Check your internet connection"
            is SerializationException -> "Unexpected server response"
            else -> localizedMessage ?: "Something went wrong"
        }
    }
}