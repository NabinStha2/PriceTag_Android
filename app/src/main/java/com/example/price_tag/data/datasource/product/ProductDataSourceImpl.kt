package com.example.price_tag.data.datasource.product

import com.example.price_tag.data.api.ApiService
import com.example.price_tag.data.model.product.ProductResponse
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
    ): Response<ProductResponse> {
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
    ): Response<ProductResponse> {
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