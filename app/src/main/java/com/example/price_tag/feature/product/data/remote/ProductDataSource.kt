package com.example.price_tag.feature.product.data.remote

import com.example.price_tag.feature.product.data.model.ProductResponseDto
import com.example.price_tag.feature.product.data.model.ProductDetailsRatingReviewsResponseDto
import com.example.price_tag.feature.product.data.model.SingleProductDetailsResponseDto
import retrofit2.Response

interface ProductDataSource {

    suspend fun getAllSubCategoryProducts(
        subCategoryId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Response<ProductResponseDto>

    suspend fun getAllSubCategorySearchProducts(subCategoryId: Long,
                                                page: Int?,
                                                limit: Int?,
                                                sortBy: String?,
                                                order: String?,
                                                name: String?): Response<ProductResponseDto>

    suspend fun getSingleProductDetails(productId: Long): Response<SingleProductDetailsResponseDto>
    suspend fun getProductDetailsRatingReviews(
        productId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Response<ProductDetailsRatingReviewsResponseDto>

}