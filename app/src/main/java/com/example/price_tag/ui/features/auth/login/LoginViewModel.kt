package com.example.price_tag.ui.features.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.core.utils.SharedPreferencesUtils
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.domain.model.auth.User
import com.example.price_tag.domain.usecase.auth.LoginUseCase
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase,
                                         private val sharedPreferencesUtils: SharedPreferencesUtils,
                                         private val navigationCoordinator: NavigationCoordinator) :
    ViewModel() {
    val spUtils: SharedPreferencesUtils = sharedPreferencesUtils

    var email = mutableStateOf("david@example.com")
    var password = mutableStateOf("Password123")
    var obscurePassword = mutableStateOf(true)
    var emailError = mutableStateOf<String?>(null)

    private val _loginData: MutableStateFlow<ResourceState<User>> =
        MutableStateFlow(ResourceState.Initial())
    val loginData: StateFlow<ResourceState<User>> = _loginData

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase(loginRequest).collectLatest { loginResponse ->
                _loginData.value = loginResponse
                if (loginResponse is ResourceState.Success) {
                    val user = loginResponse.data
                    spUtils.save(spUtils.bearerToken,
                                 user.accessToken)
                    spUtils.save(spUtils.refreshToken,
                                 user.refreshToken)

                    navigationCoordinator.navigateAndClear(NavigationItem.MainGraph)
                }
            }
        }
    }

    fun onSignUpClick() {
        viewModelScope.launch {
            navigationCoordinator.navigate(NavigationItem.SignUpView)
        }
    }

    fun resetLoginResponse() {
        _loginData.value = ResourceState.Initial()
    }

    companion object {
        const val TAG = "LoginViewModel"
    }

}
