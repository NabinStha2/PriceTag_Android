package com.example.price_tag.feature.product.data.repository

import android.util.Log
import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.feature.product.data.mapper.toDomain
import com.example.price_tag.core.common.data.model.CommonErrorResponse
import com.example.price_tag.feature.product.data.model.ProductResponseDto
import com.example.price_tag.feature.product.data.remote.ProductDataSource
import com.example.price_tag.feature.product.domain.model.ProductDetailsRatingReviews
import com.example.price_tag.feature.product.domain.model.Products
import com.example.price_tag.feature.product.domain.model.SingleProductDetails
import com.example.price_tag.feature.product.domain.repository.ProductRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val json: Json,
    private val dataStoreManager: DataStoreManager,
) : ProductRepository {

    override suspend fun getAllSubCategoryProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Flow<ResourceState<Products>> = flow {
        emit(ResourceState.Loading)
        val response = productDataSource.getAllSubCategoryProducts(
            subCategoryId,
            page,
            limit,
            sortBy,
            order,
        )
        if (response.isSuccessful && response.body() != null) {
            val responseBody: Products = response.body()!!
                .toDomain()
            emit(ResourceState.Success(responseBody))
        } else { // If the token is invalid/expired, clear stored auth tokens to trigger logout flow
            if (response.code() == 401) {
                try {
                    dataStoreManager.clearAuthTokens()
                } catch (_: Exception) { // ignore
                }
            }
            val errorBody = response.errorBody()
                ?.string()
            val message = parseError(errorBody) ?: "Failed to fetch products"
            emit(ResourceState.Error(message))
        }
    }.catch { e ->
        Log.i("oeedaelnfljaen",
              e.message.toString())
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    override suspend fun getAllSubCategorySearchProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
        name: String?,
    ): Flow<ResourceState<Products>> = flow {
        emit(ResourceState.Loading)
        val response = productDataSource.getAllSubCategorySearchProducts(subCategoryId,
                                                                         page,
                                                                         limit,
                                                                         sortBy,
                                                                         order,
                                                                         name)
        if (response.isSuccessful && response.body() != null) {
            val responseBody: ProductResponseDto = response.body()!!
            val productList: Products = responseBody.toDomain()
            emit(ResourceState.Success(productList))
        } else { // If token expired/unauthorized, clear auth tokens
            if (response.code() == 401) {
                try {
                    dataStoreManager.clearAuthTokens()
                } catch (_: Exception) {
                }
            }
            val errorBody = response.errorBody()
                ?.string()
            val message = parseError(errorBody) ?: "Failed to fetch search products"
            emit(ResourceState.Error(message))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    override suspend fun getSingleProductDetails(productId: Long): Flow<ResourceState<SingleProductDetails>> =
        flow {
            emit(ResourceState.Loading)
            val response = productDataSource.getSingleProductDetails(productId)
            if (response.isSuccessful && response.body() != null) {
                val singleProductDetails: SingleProductDetails = response.body()!!
                    .toDomain()
                Log.i("getSingleProductDetails",
                      "Product Details: ${singleProductDetails.colorSizeVariantGroup}")
                emit(ResourceState.Success(singleProductDetails))
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

    override suspend fun getProductDetailsRatingReviews(
        productId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Flow<ResourceState<ProductDetailsRatingReviews>> = flow {
        emit(ResourceState.Loading)
        val response = productDataSource.getProductDetailsRatingReviews(
            productId,
            page,
            limit,
            sortBy,
            order,
        )

        if (response.isSuccessful && response.body() != null) {
            val productDetailsRatingReviews: ProductDetailsRatingReviews = response.body()!!
                .toDomain();
            emit(ResourceState.Success(productDetailsRatingReviews))
        } else {
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
