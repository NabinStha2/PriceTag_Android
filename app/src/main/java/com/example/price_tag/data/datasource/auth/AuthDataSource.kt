package com.example.price_tag.data.datasource.auth

import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.model.auth.SignUpRequest
import retrofit2.Response

interface AuthDataSource {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>

    suspend fun register(signUpRequest: SignUpRequest): Response<LoginResponse>
}