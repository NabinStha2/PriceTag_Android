package com.example.price_tag.data.repository.product

import android.util.Log
import com.example.price_tag.data.datasource.product.ProductDataSource
import com.example.price_tag.data.model.CommonErrorResponse
import com.example.price_tag.data.model.product.ProductResponse
import com.example.utilites.ResourceState
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@OptIn(ExperimentalStdlibApi::class)
class ProductRepository @Inject constructor(private val productDataSource: ProductDataSource) {

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val jsonErrorAdapter: JsonAdapter<CommonErrorResponse> =
        moshi.adapter<CommonErrorResponse>()

    suspend fun getAllSubCategoryProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Flow<ResourceState<ProductResponse>> {
        return flow {
            emit(ResourceState.Loading())
            val response = productDataSource.getAllSubCategoryProducts(
                subCategoryId,
                page,
                limit,
                sortBy,
                order
            )
            Log.i(
                "ProductRepository getAllSubCategoryProducts",
                "response => ${response.body()}"
            )
            if (response.isSuccessful && response.body() != null) emit(
                ResourceState.Success(
                    response.body()!!
                )
            )
            else {
                val commonErrorResponse: CommonErrorResponse? =
                    response.errorBody()?.string()?.let { jsonErrorAdapter.fromJson(it) }
                Log.e(
                    "ProductRepository getAllSubCategoryProducts",
                    commonErrorResponse?.message ?: "Error while logging in"
                )
                emit(ResourceState.Error(commonErrorResponse?.message ?: "Error while logging in"))
            }
        }.catch { err ->
            Log.e(
                "ProductRepository getAllSubCategoryProducts",
                err.toString()
            )
            emit(ResourceState.Error(err.localizedMessage ?: "Something went wrong"))
        }
    }

    suspend fun getAllSubCategorySearchProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
        name: String?,
    ): Flow<ResourceState<ProductResponse>> {
        return flow<ResourceState<ProductResponse>> {
            emit(ResourceState.Loading())
            val response = productDataSource.getAllSubCategorySearchProducts(
                subCategoryId,
                page,
                limit,
                sortBy,
                order,
                name
            )
            Log.i(
                "ProductRepository getAllSubCategorySearchProducts",
                "response => ${response.body()}"
            )
            if (response.isSuccessful && response.body() != null) emit(
                ResourceState.Success(
                    response.body()!!
                )
            )
            else {
                val commonErrorResponse: CommonErrorResponse? =
                    response.errorBody()?.string()?.let { jsonErrorAdapter.fromJson(it) }
                Log.e(
                    "ProductRepository getAllSubCategoryProducts",
                    commonErrorResponse?.message ?: "Error while logging in"
                )
                emit(ResourceState.Error(commonErrorResponse?.message ?: "Error while logging in"))

            }
        }.catch { err ->
            Log.e(
                "ProductRepository getAllSubCategorySearchProducts",
                err.toString()
            )
            emit(ResourceState.Error(err.localizedMessage ?: "Something went wrong"))
        }
    }

}