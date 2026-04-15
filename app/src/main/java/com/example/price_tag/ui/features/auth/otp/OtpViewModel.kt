package com.example.price_tag.ui.features.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.data.model.auth.OtpRequest
import com.example.price_tag.data.repository.auth.AuthRepository
import com.example.price_tag.ui.navigation.NavEvent
import com.example.price_tag.ui.navigation.NavigationCoordinator
import com.example.price_tag.ui.navigation.NavigationItem
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
    private val authRepository: AuthRepository,
    private val navigationCoordinator: NavigationCoordinator
) : ViewModel() {

    private val _verifyOtpData: MutableStateFlow<ResourceState<LoginResponse>> =
        MutableStateFlow(ResourceState.Initial())
    val verifyOtpData: StateFlow<ResourceState<LoginResponse>> = _verifyOtpData

    fun verifyOtp(otpRequest: OtpRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.verifyOtp(otpRequest = otpRequest)
                .collectLatest { otpResponse ->
                    _verifyOtpData.value = otpResponse
                    if (otpResponse is ResourceState.Success) {
                        navigationCoordinator.navigate(
                            NavEvent.Navigate(NavigationItem.MainView) {
                                popUpTo(NavigationItem.LoginView) { inclusive = true }
                            }
                        )
                    }
                }
        }
    }

    fun resetVerifyOtpResponse() {
        _verifyOtpData.value = ResourceState.Initial()
    }

    companion object {
        const val TAG = "OtpViewModel"
    }

}