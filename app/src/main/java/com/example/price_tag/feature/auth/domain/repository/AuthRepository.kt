package com.example.price_tag.feature.auth.domain.repository

import com.example.price_tag.feature.auth.data.model.LoginRequest
import com.example.price_tag.feature.auth.data.model.OtpRequest
import com.example.price_tag.feature.auth.data.model.SignUpRequest
import com.example.price_tag.feature.auth.domain.model.User
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<ResourceState<User>>
    suspend fun register(signUpRequest: SignUpRequest): Flow<ResourceState<User>>
    suspend fun verifyOtp(otpRequest: OtpRequest): Flow<ResourceState<User>>
}
