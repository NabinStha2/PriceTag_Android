package com.example.price_tag.feature.cart.data.repository

import android.util.Log
import com.example.price_tag.core.common.data.model.CommonErrorResponse
import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.feature.cart.data.mapper.toDomain
import com.example.price_tag.feature.cart.data.model.CartResponseDto
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
        val response: Response<CartResponseDto> = cartDataSource.getAllCartItems()
        if (response.isSuccessful && response.body() != null) {
            val cart: Cart = response.body()!!
                .toDomain()

            Log.i("getAllCartItems",
                  "All Cart Details: $cart")

            emit(ResourceState.Success(cart))
        } else { // If unauthorized, clear tokens to force logout
            if (response.code() == 401) {
                try {
                    dataStoreManager.clearAuthTokens()
                } catch (_: Exception) {
                }
            }
            val errorBody = response.errorBody()
                ?.string()
            val message = parseError(errorBody) ?: "Failed to fetch product details"
            emit(ResourceState.Error(message))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    private fun parseError(errorBody: String?): String? = errorBody?.let {
        try {
            json.decodeFromString<CommonErrorResponse>(it).message
        } catch (_: Exception) {
            null
        }
    }
}
