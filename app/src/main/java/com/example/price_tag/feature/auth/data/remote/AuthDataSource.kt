package com.example.price_tag.feature.auth.data.remote

import com.example.price_tag.feature.auth.data.model.LoginRequest
import com.example.price_tag.feature.auth.data.model.LoginResponse
import com.example.price_tag.feature.auth.data.model.OtpRequest
import com.example.price_tag.feature.auth.data.model.SignUpRequest
import retrofit2.Response

interface AuthDataSource {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>

    suspend fun register(signUpRequest: SignUpRequest): Response<LoginResponse>

    suspend fun verifyOtp(otpRequest: OtpRequest): Response<LoginResponse>
}
