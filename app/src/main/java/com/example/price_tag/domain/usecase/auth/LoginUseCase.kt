package com.example.price_tag.domain.usecase.auth

import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.domain.model.auth.User
import com.example.price_tag.domain.repository.auth.AuthRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(loginRequest: LoginRequest): Flow<ResourceState<User>> {
        return repository.login(loginRequest)
    }
}
