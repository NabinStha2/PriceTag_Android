package com.example.price_tag.ui.repository.auth

import android.util.Log
import com.example.price_tag.data.datasource.auth.AuthDataSource
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.model.auth.OtpRequest
import com.example.price_tag.data.model.auth.SignUpRequest
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource
) {
    suspend fun login(loginRequest: LoginRequest): Flow<ResourceState<LoginResponse>> {
        return flow {
            emit(ResourceState.Loading())
            val response = authDataSource.login(loginRequest = loginRequest)
            Log.i("AuthRepository login", "response => ${response.body()}")
            if (response.isSuccessful && response.body() != null) emit(
                ResourceState.Success(
                    response.body()!!
                )
            )
            else emit(ResourceState.Error("Error while logging in"))
        }.catch { err ->
            emit(ResourceState.Error(err.localizedMessage ?: "Something went wrong"))
        }

    }

    suspend fun register(signUpRequest: SignUpRequest): Flow<ResourceState<LoginResponse>> {
        return flow {
            emit(ResourceState.Loading())
            val response = authDataSource.register(signUpRequest = signUpRequest)
            if (response.isSuccessful && response.body() != null) emit(
                ResourceState.Success(
                    response.body()!!
                )
            )
            else emit(ResourceState.Error(response.body()?.message ?: "Error while registering"))
        }.catch { err ->
            emit(ResourceState.Error(err.localizedMessage ?: "Something went wrong"))
        }
    }

    suspend fun verifyOtp(otpRequest: OtpRequest): Flow<ResourceState<LoginResponse>> {
        return flow {
            emit(ResourceState.Loading())
            val response = authDataSource.verifyOtp(otpRequest = otpRequest)
            Log.e("AuthRepository VerifyOtp", "Response  => ${response.body()}")
            if (response.isSuccessful && response.body() != null) emit(
                ResourceState.Success(
                    response.body()!!
                )
            )
            else emit(ResourceState.Error(response.body()?.message ?: "Error while registering"))
        }.catch { err ->
            emit(ResourceState.Error(err.localizedMessage ?: "Something went wrong"))
        }
    }
}