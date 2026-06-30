package com.example.price_tag.data.repository.auth

import android.util.Log
import com.example.price_tag.data.mapper.auth.toDomain
import com.example.price_tag.data.model.common.CommonErrorResponse
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.OtpRequest
import com.example.price_tag.data.model.auth.SignUpRequest
import com.example.price_tag.data.remote.datasource.auth.AuthDataSource
import com.example.price_tag.domain.model.auth.User
import com.example.price_tag.domain.repository.auth.AuthRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource,
                                             private val json: Json) : AuthRepository {

    //    private val jsonErrorAdapter: JsonAdapter<CommonErrorResponse> =
    //        moshi.adapter(CommonErrorResponse::class.java)

    override suspend fun login(loginRequest: LoginRequest): Flow<ResourceState<User>> = flow {
        val response = authDataSource.login(loginRequest)
        if (response.isSuccessful && response.body() != null) {
            Log.d("AuthRepositoryImpl",
                  "login: ${response.body()}")
            response.body()
                ?.let { emit(ResourceState.Success(it.toDomain())) }
        } else {
            val errorBody = response.errorBody()
                ?.string()
            val message = parseError(errorBody) ?: "Login failed"
            emit(ResourceState.Error(message))
        }
    }.catch { e ->
        Log.e("AuthRepositoryImpl",
              "login: ${e.localizedMessage}")
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    override suspend fun register(signUpRequest: SignUpRequest): Flow<ResourceState<User>> = flow {
        emit(ResourceState.Loading)
        val response = authDataSource.register(signUpRequest)
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!
                                           .toDomain()))
        } else {
            val errorBody = response.errorBody()
                ?.string()
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
            emit(ResourceState.Success(response.body()!!
                                           .toDomain()))
        } else {
            val errorBody = response.errorBody()
                ?.string()
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
