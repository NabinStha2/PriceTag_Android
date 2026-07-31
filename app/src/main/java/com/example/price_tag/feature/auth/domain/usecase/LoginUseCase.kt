package com.example.price_tag.feature.auth.domain.usecase

import com.example.price_tag.feature.auth.data.model.LoginRequest
import com.example.price_tag.feature.auth.domain.model.User
import com.example.price_tag.feature.auth.domain.repository.AuthRepository
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
