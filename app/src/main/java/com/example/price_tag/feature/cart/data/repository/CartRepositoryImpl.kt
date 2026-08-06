package com.example.price_tag.feature.cart.data.repository

import com.example.price_tag.core.common.data.model.CommonErrorResponse
import com.example.price_tag.core.common.data.model.CommonResponseDto
import com.example.price_tag.core.common.domain.model.CommonResponse
import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.feature.cart.data.mapper.toDomain
import com.example.price_tag.feature.cart.data.model.AddCartItemRequestDto
import com.example.price_tag.feature.cart.data.model.UpdateCartItemQuantityRequestDto
import com.example.price_tag.feature.cart.data.remote.CartDataSource
import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.price_tag.feature.cart.domain.repository.CartRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataSource: CartDataSource,
    private val json: Json,
    private val dataStoreManager: DataStoreManager,
) : CartRepository {

    override suspend fun getAllCartItems(): Flow<ResourceState<Cart>> = flow {
        emit(ResourceState.Loading)
        val response = cartDataSource.getAllCartItems()
        if (response.isSuccessful && response.body() != null) {
            val cart = response.body()!!
                .toDomain()
            if (cart != null) {
                emit(ResourceState.Success(cart))
            } else {
                emit(ResourceState.Error("Cart data was empty or malformed"))
            }
        } else {
            if (response.code() == 401) {
                try {
                    dataStoreManager.clearAuthTokens()
                } catch (_: Exception) {
                }
            }
            val errorBody = response.errorBody()
                ?.string()
            val message = parseError(errorBody) ?: "Failed to fetch cart details"
            emit(ResourceState.Error(message))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    override suspend fun addItemToCart(addCartItemRequestDto: AddCartItemRequestDto): Flow<ResourceState<CommonResponse>> {
        return flow<ResourceState<CommonResponse>> {
            emit(ResourceState.Loading)
            val response: Response<CommonResponseDto> =
                cartDataSource.addItemToCart(addCartItemRequestDto)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.Success(CommonResponse(
                    message = response.body()!!.message,
                    success = response.body()!!.success,
                    statusCode = response.body()!!.statusCode,
                )))
            } else {
                val errorBody = response.errorBody()
                    ?.string()
                val message = parseError(errorBody) ?: "Failed to add item to cart"
                emit(ResourceState.Error(message))
            }
        }.catch { e ->
            emit(ResourceState.Error(e.message ?: "An error occurred"))
        }
    }

    override suspend fun removeCartItem(cartItemId: Long): Flow<ResourceState<CommonResponse>> {
        return flow {
            emit(ResourceState.Loading)
            val response: Response<CommonResponseDto> = cartDataSource.removeCartItem(cartItemId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.Success(CommonResponse(
                    message = response.body()!!.message,
                    statusCode = response.body()!!.statusCode,
                    success = response.body()!!.success,
                )))
            } else {
                val errorBody = response.errorBody()
                    ?.string()
                val message = parseError(errorBody) ?: "Failed to remove item from cart"
                emit(ResourceState.Error(message))
            }
        }.catch { e ->
            emit(ResourceState.Error(e.message ?: "An error occurred"))
        }
    }

    override suspend fun updateCartItemQuantity(updateCartItemQuantityRequestDto: UpdateCartItemQuantityRequestDto): Flow<ResourceState<CommonResponse>> {
        return flow {
            emit(ResourceState.Loading)
            val response: Response<CommonResponseDto> =
                cartDataSource.updateCartItemQuantity(updateCartItemQuantityRequestDto)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.Success(CommonResponse(
                    message = response.body()!!.message,
                    statusCode = response.body()!!.statusCode,
                    success = response.body()!!.success,
                )))
            } else {
                val errorBody = response.errorBody()
                    ?.string()
                val message = parseError(errorBody) ?: "Failed to update cart item quantity"
                emit(ResourceState.Error(message))
            }
        }.catch { e ->
            emit(ResourceState.Error(e.message ?: "An error occurred"))
        }
    }

    private fun parseError(errorBody: String?): String? = errorBody?.let {
        try {
            json.decodeFromString<CommonErrorResponse>(it).message
        } catch (_: Exception) {
            null
        }
    }
}
