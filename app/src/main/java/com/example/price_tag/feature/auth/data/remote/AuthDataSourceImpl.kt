package com.example.price_tag.feature.auth.data.remote

import com.example.price_tag.core.network.ApiService
import com.example.price_tag.feature.auth.data.model.LoginRequest
import com.example.price_tag.feature.auth.data.model.LoginResponse
import com.example.price_tag.feature.auth.data.model.OtpRequest
import com.example.price_tag.feature.auth.data.model.SignUpRequest
import retrofit2.Response
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(private val apiService: ApiService) : AuthDataSource {

    override suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return apiService.login(loginRequest = loginRequest)
    }

    override suspend fun register(signUpRequest: SignUpRequest): Response<LoginResponse> {
        return apiService.register(signUpRequest = signUpRequest)
    }

    override suspend fun verifyOtp(otpRequest: OtpRequest): Response<LoginResponse> {
        return apiService.verifyOtp(otpRequest = otpRequest)
    }

}
