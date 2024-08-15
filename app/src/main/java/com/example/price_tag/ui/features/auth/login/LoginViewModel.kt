package com.example.price_tag.ui.features.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.repository.auth.AuthRepository
import com.example.price_tag.utils.SharedPreferencesUtils
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPreferencesUtils: SharedPreferencesUtils
) : ViewModel() {
    val spUtils: SharedPreferencesUtils = sharedPreferencesUtils

    var email = mutableStateOf("admin@gmail.com")
    var password = mutableStateOf("hello123")
    var obscurePassword = mutableStateOf(true)
    var emailError = mutableStateOf<String?>(null)

    private val _loginData: MutableStateFlow<ResourceState<LoginResponse>> =
        MutableStateFlow(ResourceState.Initial())
    val loginData: StateFlow<ResourceState<LoginResponse>> = _loginData


    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(loginRequest).collectLatest { loginReponse ->
                _loginData.value = loginReponse
            }
        }
    }

    // Method to reset login response state
    fun resetLoginResponse() {
        _loginData.value = ResourceState.Initial()
    }

    companion object {
        const val TAG = "LoginViewModel"
    }

}