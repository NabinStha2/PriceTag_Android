package com.example.price_tag.data.repository.auth

import android.util.Log
import com.example.price_tag.data.datasource.auth.AuthDataSource
import com.example.price_tag.data.model.CommonErrorResponse
import com.example.price_tag.data.model.auth.LoginData
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.model.auth.OtpRequest
import com.example.price_tag.data.model.auth.SignUpRequest
import com.example.utilites.ResourceState
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@OptIn(ExperimentalStdlibApi::class)
class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource
) {
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val jsonErrorAdapter: JsonAdapter<CommonErrorResponse> =
        moshi.adapter<CommonErrorResponse>()

    suspend fun login(loginRequest: LoginRequest): Flow<ResourceState<LoginResponse>> {
        return flow {
            emit(ResourceState.Loading())
            val response = authDataSource.login(loginRequest = loginRequest)
            Log.i("AuthRepository login", "response => ${response.body()}")
            if (response.isSuccessful && response.body() != null) {
                emit(
                    ResourceState.Success(
                        response.body()!!
                    )
                )
            }
            else {
                val errorBody = response.errorBody()?.string() // Read it once and store it
                val commonErrorResponse: CommonErrorResponse? =
                    errorBody?.let {
                        jsonErrorAdapter.fromJson(it)
                    }
                Log.e("AuthRepository login", commonErrorResponse?.message ?: "Error while logging in")
                emit(ResourceState.Error(commonErrorResponse?.message ?: "Error while logging in"))
            }
        }.catch { err ->
            Log.e("AuthRepository login", err.toString() )
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
            else {
                val commonErrorResponse: CommonErrorResponse? =
                    response.errorBody()?.string()?.let { jsonErrorAdapter.fromJson(it) }
                emit(ResourceState.Error(commonErrorResponse?.message ?: "Error while registering"))
            }
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
            else {
                val commonErrorResponse: CommonErrorResponse? =
                    response.errorBody()?.string()?.let { jsonErrorAdapter.fromJson(it) }
                emit(ResourceState.Error(commonErrorResponse?.message ?: "Error while registering"))
            }
        }.catch { err ->
            emit(ResourceState.Error(err.localizedMessage ?: "Something went wrong"))
        }
    }
}