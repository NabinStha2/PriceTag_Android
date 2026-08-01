package com.example.price_tag.core.network

import com.example.price_tag.feature.auth.data.model.LoginRequest
import com.example.price_tag.feature.auth.data.model.LoginResponse
import com.example.price_tag.feature.auth.data.model.OtpRequest
import com.example.price_tag.feature.auth.data.model.SignUpRequest
import com.example.price_tag.feature.cart.data.model.CartResponseDto
import com.example.price_tag.feature.category.data.model.CategoryResponse
import com.example.price_tag.feature.category.data.model.SubCategoryResponse
import com.example.price_tag.feature.product.data.model.ProductDetailsRatingReviewsResponseDto
import com.example.price_tag.feature.product.data.model.ProductResponseDto
import com.example.price_tag.feature.product.data.model.SingleProductDetailsResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body signUpRequest: SignUpRequest): Response<LoginResponse>

    @POST("auth/verify-otp")
    suspend fun verifyOtp(@Body otpRequest: OtpRequest): Response<LoginResponse>

    @GET(value = "category/")
    suspend fun getAllCategories(): Response<CategoryResponse>

    @GET(value = "subcategory/{category_id}")
    suspend fun getSubCategoriesWithSelectedCategory(@Path("category_id") categoryId: Long): Response<SubCategoryResponse>

    @GET(value = "product/subcategory/{subcategory_id}")
    suspend fun getAllSubCategoryProducts(
        @Path("subcategory_id") subcategoryId: Long,
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10,
        @Query("sortBy") sortBy: String? = "name",
        @Query("order") order: String? = "asc",
    ): Response<ProductResponseDto>


    @GET(value = "product/subcategory/{subcategory_id}/search")
    suspend fun getAllSubCategorySearchProducts(
        @Path("subcategory_id") subcategoryId: Long,
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10,
        @Query("sortBy") sortBy: String? = "name",
        @Query("order") order: String? = "asc",
        @Query("name") name: String? = "",
    ): Response<ProductResponseDto>

    @GET(value = "product/{product_id}")
    suspend fun getSingleProductDetails(@Path("product_id") productId: Long): Response<SingleProductDetailsResponseDto>

    @GET(value = "rating-review/product/{product_id}")
    suspend fun getProductDetailsRatingReviews(
        @Path(value = "product_id") productId: Long,
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10,
        @Query("sortBy") sortBy: String? = "createdAt",
        @Query("order") order: String? = "desc",
    ): Response<ProductDetailsRatingReviewsResponseDto>

    @GET(value = "cart")
    suspend fun getAllCartItems(): Response<CartResponseDto>

}