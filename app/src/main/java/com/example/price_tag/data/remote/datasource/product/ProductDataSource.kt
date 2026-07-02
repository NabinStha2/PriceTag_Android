package com.example.price_tag.data.remote.datasource.product

import com.example.price_tag.data.model.product.ProductResponseDto
import com.example.price_tag.data.model.product_details.SingleProductDetailsResponseDto
import retrofit2.Response

interface ProductDataSource {

    suspend fun getAllSubCategoryProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Response<ProductResponseDto>

    suspend fun getAllSubCategorySearchProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
        name: String?
    ): Response<ProductResponseDto>

    suspend fun getSingleProductDetails(productId: Long): Response<SingleProductDetailsResponseDto>

}