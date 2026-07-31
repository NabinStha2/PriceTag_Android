package com.example.price_tag.feature.product.domain.repository

import com.example.price_tag.feature.product.domain.model.ProductDetailsRatingReviews
import com.example.price_tag.feature.product.domain.model.Products
import com.example.price_tag.feature.product.domain.model.SingleProductDetails
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllSubCategoryProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Flow<ResourceState<Products>>

    suspend fun getAllSubCategorySearchProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
        name: String?,
    ): Flow<ResourceState<Products>>

    suspend fun getSingleProductDetails(productId: Long): Flow<ResourceState<SingleProductDetails>>

    suspend fun getProductDetailsRatingReviews(
        productId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Flow<ResourceState<ProductDetailsRatingReviews>>
}
