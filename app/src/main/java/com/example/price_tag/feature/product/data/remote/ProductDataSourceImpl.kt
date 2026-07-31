package com.example.price_tag.feature.product.data.remote

import com.example.price_tag.feature.product.data.model.ProductResponseDto
import com.example.price_tag.feature.product.data.model.ProductDetailsRatingReviewsResponseDto
import com.example.price_tag.feature.product.data.model.SingleProductDetailsResponseDto
import com.example.price_tag.core.network.ApiService
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
            order,
        )
    }

    override suspend fun getAllSubCategorySearchProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
        name: String?,
    ): Response<ProductResponseDto> {
        return apiService.getAllSubCategorySearchProducts(
            subCategoryId,
            page,
            limit,
            sortBy,
            order,
            name,
        )
    }

    override suspend fun getSingleProductDetails(productId: Long): Response<SingleProductDetailsResponseDto> {
        return apiService.getSingleProductDetails(productId)
    }

    override suspend fun getProductDetailsRatingReviews(
        productId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Response<ProductDetailsRatingReviewsResponseDto> {
        return apiService.getProductDetailsRatingReviews(
            productId,
            page,
            limit,
            sortBy,
            order,
        )
    }

}