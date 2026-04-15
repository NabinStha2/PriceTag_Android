package com.example.price_tag.domain.repository.product

import com.example.price_tag.domain.model.product.Product
import com.example.price_tag.domain.model.product.ProductList
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllSubCategoryProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?
    ): Flow<ResourceState<ProductList>>

    suspend fun getAllSubCategorySearchProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
        name: String?
    ): Flow<ResourceState<ProductList>>

    suspend fun getSingleProductDetails(productId: Long): Flow<ResourceState<Product>>
}
