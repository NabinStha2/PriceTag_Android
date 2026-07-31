package com.example.price_tag.feature.auth.domain.usecase

import com.example.price_tag.feature.auth.data.model.OtpRequest
import com.example.price_tag.feature.auth.domain.model.User
import com.example.price_tag.feature.auth.domain.repository.AuthRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(otpRequest: OtpRequest): Flow<ResourceState<User>> {
        return repository.verifyOtp(otpRequest)
    }
}
