package com.example.price_tag.feature.checkout.data.repository

import com.example.price_tag.core.common.data.model.ApiResponseDto
import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.core.network.error.ApiErrorParser
import com.example.price_tag.core.network.error.toUserMessage
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
import retrofit2.Response
import java.util.concurrent.CancellationException
import javax.inject.Inject

class CheckoutRepositoryImpl @Inject constructor(
    private val checkoutDataSource: CheckoutDataSource,
    private val dataStoreManager: DataStoreManager,
    private val apiErrorParser: ApiErrorParser,
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
                val message = apiErrorParser.parseMessage(
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
}