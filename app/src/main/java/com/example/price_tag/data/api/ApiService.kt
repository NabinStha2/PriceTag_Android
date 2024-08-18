package com.example.price_tag.data.api

import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.model.auth.OtpRequest
import com.example.price_tag.data.model.auth.SignUpRequest
import com.example.price_tag.data.model.category.CategoryResponse
import com.example.price_tag.data.model.category.SubCategoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body signUpRequest: SignUpRequest): Response<LoginResponse>

    @POST("auth/verify-otp")
    suspend fun verifyOtp(@Body otpRequest: OtpRequest): Response<LoginResponse>

    @GET(value = "category/")
    suspend fun getAllCategories(): Response<CategoryResponse>

    @GET(value = "subcategory/{categoryId}")
    suspend fun getAllSubCategories(@Path("categoryId") categoryId: Long): Response<SubCategoryResponse>


}