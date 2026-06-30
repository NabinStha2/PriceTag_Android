package com.example.price_tag.data.remote.datasource.product

import com.example.price_tag.data.model.product.ProductResponseDto
import com.example.price_tag.data.remote.api.ApiService
import com.example.price_tag.data.model.product_details.ProductDetailsResponse
import retrofit2.Response
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    ProductDataSource {

    override suspend fun getAllSubCategoryProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Response<ProductResponseDto> {
        return apiService.getAllSubCategoryProducts(
            subCategoryId,
            page,
            limit,
            sortBy,
            order
        )
    }

    override suspend fun getAllSubCategorySearchProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
        name: String?
    ): Response<ProductResponseDto> {
        return apiService.getAllSubCategorySearchProducts(
            subCategoryId,
            page,
            limit,
            sortBy,
            order,
            name
        )
    }

    override suspend fun getSingleProductDetails(productId: Long): Response<ProductDetailsResponse> {
        return apiService.getSingleProductDetails(productId)
    }

}