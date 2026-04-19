package com.example.price_tag.data.repository.auth

import com.example.price_tag.data.remote.datasource.auth.AuthDataSource
import com.example.price_tag.data.mapper.auth.toDomain
import com.example.price_tag.data.model.CommonErrorResponse
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.OtpRequest
import com.example.price_tag.data.model.auth.SignUpRequest
import com.example.price_tag.domain.model.auth.User
import com.example.price_tag.domain.repository.auth.AuthRepository
import com.example.utilites.ResourceState
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val moshi: Moshi
) : AuthRepository {

    private val jsonErrorAdapter: JsonAdapter<CommonErrorResponse> =
        moshi.adapter(CommonErrorResponse::class.java)

    override suspend fun login(loginRequest: LoginRequest): Flow<ResourceState<User>> = flow {
        emit(ResourceState.Loading())
        val response = authDataSource.login(loginRequest)
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!.toDomain()))
        } else {
            val errorBody = response.errorBody()?.string()
            val commonErrorResponse = errorBody?.let { jsonErrorAdapter.fromJson(it) }
            emit(ResourceState.Error(commonErrorResponse?.message ?: "Login failed"))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    override suspend fun register(signUpRequest: SignUpRequest): Flow<ResourceState<User>> = flow {
        emit(ResourceState.Loading())
        val response = authDataSource.register(signUpRequest)
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!.toDomain()))
        } else {
            val errorBody = response.errorBody()?.string()
            val commonErrorResponse = errorBody?.let { jsonErrorAdapter.fromJson(it) }
            emit(ResourceState.Error(commonErrorResponse?.message ?: "Registration failed"))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    override suspend fun verifyOtp(otpRequest: OtpRequest): Flow<ResourceState<User>> = flow {
        emit(ResourceState.Loading())
        val response = authDataSource.verifyOtp(otpRequest)
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!.toDomain()))
        } else {
            val errorBody = response.errorBody()?.string()
            val commonErrorResponse = errorBody?.let { jsonErrorAdapter.fromJson(it) }
            emit(ResourceState.Error(commonErrorResponse?.message ?: "OTP verification failed"))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }
}
