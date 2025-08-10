package com.example.price_tag.data.datasource.product

import com.example.price_tag.data.model.product.ProductResponse
import retrofit2.Response

interface ProductDataSource {

    suspend fun getAllSubCategoryProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Response<ProductResponse>

    suspend fun getAllSubCategorySearchProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
        name: String?
    ): Response<ProductResponse>

}