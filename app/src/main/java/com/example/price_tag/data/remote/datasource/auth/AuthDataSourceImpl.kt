package com.example.price_tag.data.remote.datasource.auth

import com.example.price_tag.data.remote.api.ApiService
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.model.auth.OtpRequest
import com.example.price_tag.data.model.auth.SignUpRequest
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