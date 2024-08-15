package com.example.price_tag.ui.features.auth.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.model.auth.SignUpRequest
import com.example.price_tag.data.repository.auth.AuthRepository
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

    var userName = mutableStateOf("who cares")
    var email = mutableStateOf("nabincresta246@gmail.com")
    var phoneNumber = mutableStateOf("9826484691")
    var password = mutableStateOf("hello123")
    var confirmPassword = mutableStateOf("hello123")
    var obscurePassword = mutableStateOf(true)
    var obscureConfirmPassword = mutableStateOf(true)
    var fullNameError = mutableStateOf<String?>(null)
    var emailError = mutableStateOf<String?>(null)
    var passwordError = mutableStateOf<String?>(null)
    var confirmPasswordError = mutableStateOf<String?>(null)
    var phoneNumberError = mutableStateOf<String?>(null)

//    val userName: State<String> = _userName
//    val email: State<String> = _email
//    val phoneNumber: State<String> = _phoneNumber
//    val password: State<String> = _password
//    val confirmPassword: State<String> = _confirmPassword

//    fun setEmail(newEmail: String) {
//        _email.value = newEmail
//    }


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

    fun resetSignUpResponse() {
        _signupData.value = ResourceState.Initial()
    }

    companion object {
        const val TAG = "LoginViewModel"
    }

}