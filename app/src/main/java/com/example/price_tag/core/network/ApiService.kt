package com.example.price_tag.core.network

import com.example.price_tag.core.common.data.model.ApiResponseDto
import com.example.price_tag.core.common.data.model.CommonResponseDto
import com.example.price_tag.feature.auth.data.model.LoginRequest
import com.example.price_tag.feature.auth.data.model.LoginResponse
import com.example.price_tag.feature.auth.data.model.OtpRequest
import com.example.price_tag.feature.auth.data.model.SignUpRequest
import com.example.price_tag.feature.cart.data.model.AddCartItemRequestDto
import com.example.price_tag.feature.cart.data.model.CartResponseDto
import com.example.price_tag.feature.cart.data.model.UpdateCartItemQuantityRequestDto
import com.example.price_tag.feature.category.data.model.CategoryResponse
import com.example.price_tag.feature.category.data.model.SubCategoryResponse
import com.example.price_tag.feature.checkout.data.model.request.CreateAddressRequestDto
import com.example.price_tag.feature.checkout.data.model.request.CreateOrderRequestDto
import com.example.price_tag.feature.checkout.data.model.response.AddressResponseDto
import com.example.price_tag.feature.checkout.data.model.response.CreateOrderResponseDto
import com.example.price_tag.feature.checkout.data.model.response.PaymentIntentResponseDto
import com.example.price_tag.feature.product.data.model.ProductDetailsRatingReviewsResponseDto
import com.example.price_tag.feature.product.data.model.ProductResponseDto
import com.example.price_tag.feature.product.data.model.SingleProductDetailsResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    @POST(value = "cart/items")
    suspend fun addItemToCart(@Body addCartItemRequestDto: AddCartItemRequestDto): Response<CommonResponseDto>

    @DELETE(value = "cart/item/{item_id}")
    suspend fun removeCartItem(@Path("item_id") cartItemId: Long): Response<CommonResponseDto>

    @PATCH(value = "cart/item/{item_id}")
    suspend fun updateCartItemQuantity(
        @Path("item_id") cartItemId: Long,
        @Body updateCartItemQuantityRequestDto: UpdateCartItemQuantityRequestDto,
    ): Response<CommonResponseDto>

    @GET(value = "address")
    suspend fun getAddresses(): Response<ApiResponseDto<List<AddressResponseDto>>>

    @POST("address")
    suspend fun createAddress(
        @Body createAddressRequestDto: CreateAddressRequestDto,
    ): Response<ApiResponseDto<List<AddressResponseDto>>>

    @POST("orders")
    suspend fun createOrder(
        @Body createOrderRequestDto: CreateOrderRequestDto,
    ): Response<ApiResponseDto<CreateOrderResponseDto>>

    @POST("orders/{orderNumber}/payment-intent")
    suspend fun initializePayment(
        @Path("orderNumber") orderNumber: String,
    ): Response<ApiResponseDto<PaymentIntentResponseDto>>
}