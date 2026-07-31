package com.example.price_tag.feature.auth.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.feature.auth.data.model.LoginRequest
import com.example.price_tag.feature.auth.domain.model.User
import com.example.price_tag.feature.auth.domain.usecase.LoginUseCase
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase,
                                         private val dataStoreManager: DataStoreManager,
                                         private val navigationCoordinator: NavigationCoordinator) :
    ViewModel() {
    var email = mutableStateOf("david@example.com")
    var password = mutableStateOf("Password123")
    var obscurePassword = mutableStateOf(true)
    var emailError = mutableStateOf<String?>(null)

    private val _loginData: MutableStateFlow<ResourceState<User>> =
        MutableStateFlow(ResourceState.Initial)
    val loginData: StateFlow<ResourceState<User>> = _loginData

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase(loginRequest).collectLatest { loginResponse ->
                _loginData.value = loginResponse
                if (loginResponse is ResourceState.Success) {
                    val user = loginResponse.data

                    dataStoreManager.saveBearerToken(user.accessToken)
                    dataStoreManager.saveRefreshToken(user.refreshToken)

//                    navigationCoordinator.navigateAndClear(NavigationItem.MainGraph)
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
        _loginData.value = ResourceState.Initial
    }

    companion object {
        const val TAG = "LoginViewModel"
    }

}
