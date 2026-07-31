package com.example.price_tag.feature.auth.domain.usecase

import com.example.price_tag.feature.auth.data.model.SignUpRequest
import com.example.price_tag.feature.auth.domain.model.User
import com.example.price_tag.feature.auth.domain.repository.AuthRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(signUpRequest: SignUpRequest): Flow<ResourceState<User>> {
        return repository.register(signUpRequest)
    }
}
