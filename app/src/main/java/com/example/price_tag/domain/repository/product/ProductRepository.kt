package com.example.price_tag.domain.repository.product

import com.example.price_tag.domain.model.product.ProductDetailsRatingReviews
import com.example.price_tag.domain.model.product.Products
import com.example.price_tag.domain.model.product.SingleProductDetails
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
