package com.example.price_tag.ui.features.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.model.auth.OtpRequest
import com.example.price_tag.ui.repository.auth.AuthRepository
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OtpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginData: MutableStateFlow<ResourceState<LoginResponse>> =
        MutableStateFlow(ResourceState.Initial())
    val loginData: StateFlow<ResourceState<LoginResponse>> = _loginData

    fun verifyOtp(otpRequest: OtpRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.verifyOtp(otpRequest = otpRequest)
                .collectLatest { otpResponse ->
                    _loginData.value = otpResponse
                }
        }
    }

    companion object {
        const val TAG = "LoginViewModel"
    }

}