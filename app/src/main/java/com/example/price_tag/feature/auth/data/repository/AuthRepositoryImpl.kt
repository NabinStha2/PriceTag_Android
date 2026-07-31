package com.example.price_tag.feature.auth.data.repository

import android.util.Log
import com.example.price_tag.feature.auth.data.mapper.toDomain
import com.example.price_tag.core.common.data.model.CommonErrorResponse
import com.example.price_tag.feature.auth.data.model.LoginRequest
import com.example.price_tag.feature.auth.data.model.OtpRequest
import com.example.price_tag.feature.auth.data.model.SignUpRequest
import com.example.price_tag.feature.auth.data.remote.AuthDataSource
import com.example.price_tag.feature.auth.domain.model.User
import com.example.price_tag.feature.auth.domain.repository.AuthRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val json: Json
) : AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): Flow<ResourceState<User>> = flow {
        val response = authDataSource.login(loginRequest)
        if (response.isSuccessful && response.body() != null) {
            Log.d("AuthRepositoryImpl", "login: ${response.body()}")
            response.body()?.let { emit(ResourceState.Success(it.toDomain())) }
        } else {
            val errorBody = response.errorBody()?.string()
            val message = parseError(errorBody) ?: "Login failed"
            emit(ResourceState.Error(message))
        }
    }.catch { e ->
        Log.e("AuthRepositoryImpl", "login: ${e.localizedMessage}")
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    override suspend fun register(signUpRequest: SignUpRequest): Flow<ResourceState<User>> = flow {
        emit(ResourceState.Loading)
        val response = authDataSource.register(signUpRequest)
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!.toDomain()))
        } else {
            val errorBody = response.errorBody()?.string()
            val message = parseError(errorBody) ?: "Registration failed"
            emit(ResourceState.Error(message))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    override suspend fun verifyOtp(otpRequest: OtpRequest): Flow<ResourceState<User>> = flow {
        emit(ResourceState.Loading)
        val response = authDataSource.verifyOtp(otpRequest)
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!.toDomain()))
        } else {
            val errorBody = response.errorBody()?.string()
            val message = parseError(errorBody) ?: "OTP verification failed"
            emit(ResourceState.Error(message))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    private fun parseError(errorBody: String?): String? = errorBody?.let {
        try {
            json.decodeFromString<CommonErrorResponse>(it).message
        } catch (_: Exception) {
            null
        }
    }
}
