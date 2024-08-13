package com.example.price_tag.ui.features.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.model.auth.SignUpRequest
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
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) :
    ViewModel() {


    private val _signupData: MutableStateFlow<ResourceState<LoginResponse>> =
        MutableStateFlow(ResourceState.Initial())
    val signUpData: StateFlow<ResourceState<LoginResponse>> = _signupData

    fun register(signUpRequest: SignUpRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.register(signUpRequest)
                .collectLatest { signupResponse ->
                    _signupData.value = signupResponse
                }
        }
    }

    companion object {
        const val TAG = "LoginViewModel"
    }

}