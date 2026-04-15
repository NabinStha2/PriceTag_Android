package com.example.price_tag.ui.features.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.repository.auth.AuthRepository
import com.example.price_tag.ui.navigation.NavEvent
import com.example.price_tag.ui.navigation.NavigationCoordinator
import com.example.price_tag.ui.navigation.NavigationItem
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
    private val sharedPreferencesUtils: SharedPreferencesUtils,
    private val navigationCoordinator: NavigationCoordinator
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
                if (loginReponse is ResourceState.Success) {
                    val response = loginReponse.data
                    if (response.data?.accessToken != null && response.data.refreshToken != null) {
                        spUtils.save(spUtils.bearerToken, response.data.accessToken)
                        spUtils.save(spUtils.refreshToken, response.data.refreshToken)
                    }
                    
                    // PRO ARCHITECTURE: ViewModel decides where to go
                    navigationCoordinator.navigate(
                        NavEvent.Navigate(NavigationItem.MainView) {
                            popUpTo(NavigationItem.LoginView) { inclusive = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    )
                }
            }
        }
    }

    fun onSignUpClick() {
        navigationCoordinator.navigateTo(NavigationItem.SignUpView)
    }

    // Method to reset login response state
    fun resetLoginResponse() {
        _loginData.value = ResourceState.Initial()
    }

    companion object {
        const val TAG = "LoginViewModel"
    }

}