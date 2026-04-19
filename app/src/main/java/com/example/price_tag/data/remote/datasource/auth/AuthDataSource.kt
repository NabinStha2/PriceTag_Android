package com.example.price_tag.data.remote.datasource.auth

import com.example.price_tag.data.model.auth.LoginData
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.model.auth.OtpRequest
import com.example.price_tag.data.model.auth.SignUpRequest
import retrofit2.Response

interface AuthDataSource {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>

    suspend fun register(signUpRequest: SignUpRequest): Response<LoginResponse>

    suspend fun verifyOtp(otpRequest: OtpRequest): Response<LoginResponse>
}