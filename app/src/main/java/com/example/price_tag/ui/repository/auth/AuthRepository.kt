package com.example.price_tag.ui.repository.auth

import com.example.price_tag.data.datasource.auth.AuthDataSource
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.LoginResponse
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
}