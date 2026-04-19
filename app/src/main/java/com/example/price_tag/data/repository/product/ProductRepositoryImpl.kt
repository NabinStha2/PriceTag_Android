package com.example.price_tag.data.repository.product

import com.example.price_tag.data.remote.datasource.product.ProductDataSource
import com.example.price_tag.data.mapper.product.toDomain
import com.example.price_tag.data.model.CommonErrorResponse
import com.example.price_tag.domain.model.product.Product
import com.example.price_tag.domain.model.product.ProductList
import com.example.price_tag.domain.repository.product.ProductRepository
import com.example.utilites.ResourceState
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val moshi: Moshi
) : ProductRepository {

    private val jsonErrorAdapter: JsonAdapter<CommonErrorResponse> =
        moshi.adapter(CommonErrorResponse::class.java)

    override suspend fun getAllSubCategoryProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?
    ): Flow<ResourceState<ProductList>> = flow {
        emit(ResourceState.Loading())
        val response = productDataSource.getAllSubCategoryProducts(subCategoryId, page, limit, sortBy, order)
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!.toDomain()))
        } else {
            val errorBody = response.errorBody()?.string()
            val commonErrorResponse = errorBody?.let { jsonErrorAdapter.fromJson(it) }
            emit(ResourceState.Error(commonErrorResponse?.message ?: "Failed to fetch products"))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    override suspend fun getAllSubCategorySearchProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
        name: String?
    ): Flow<ResourceState<ProductList>> = flow {
        emit(ResourceState.Loading())
        val response = productDataSource.getAllSubCategorySearchProducts(subCategoryId, page, limit, sortBy, order, name)
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!.toDomain()))
        } else {
            val errorBody = response.errorBody()?.string()
            val commonErrorResponse = errorBody?.let { jsonErrorAdapter.fromJson(it) }
            emit(ResourceState.Error(commonErrorResponse?.message ?: "Failed to search products"))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    override suspend fun getSingleProductDetails(productId: Long): Flow<ResourceState<Product>> = flow {
        emit(ResourceState.Loading())
        val response = productDataSource.getSingleProductDetails(productId)
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!.toDomain()))
        } else {
            val errorBody = response.errorBody()?.string()
            val commonErrorResponse = errorBody?.let { jsonErrorAdapter.fromJson(it) }
            emit(ResourceState.Error(commonErrorResponse?.message ?: "Failed to fetch product details"))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }
}
