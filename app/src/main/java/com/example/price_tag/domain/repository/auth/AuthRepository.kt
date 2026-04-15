package com.example.price_tag.domain.repository.auth

import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.OtpRequest
import com.example.price_tag.data.model.auth.SignUpRequest
import com.example.price_tag.domain.model.auth.User
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<ResourceState<User>>
    suspend fun register(signUpRequest: SignUpRequest): Flow<ResourceState<User>>
    suspend fun verifyOtp(otpRequest: OtpRequest): Flow<ResourceState<User>>
}
