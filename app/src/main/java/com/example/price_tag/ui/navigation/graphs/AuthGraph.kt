package com.example.price_tag.ui.navigation.graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.price_tag.ui.components.SelectedCategoryViewModel
import com.example.price_tag.ui.features.auth.login.LoginView
import com.example.price_tag.ui.features.auth.login.LoginViewModel
import com.example.price_tag.ui.features.auth.otp.OtpView
import com.example.price_tag.ui.features.auth.otp.OtpViewModel
import com.example.price_tag.ui.features.auth.signup.SignUpView
import com.example.price_tag.ui.features.auth.signup.SignUpViewModel
import com.example.price_tag.ui.navigation.NavigationItem
import com.example.price_tag.utils.animatedComposable
import kotlinx.serialization.Serializable

@Serializable
data object AuthGraph

fun NavGraphBuilder.authGraph(
    navController: NavController,
    selectedCategoryViewModel: SelectedCategoryViewModel
) {
    navigation<AuthGraph>(startDestination = NavigationItem.LoginView) {
        animatedComposable<NavigationItem.LoginView> {
            val vm: LoginViewModel = hiltViewModel()
            LoginView(navController, vm)
        }

        animatedComposable<NavigationItem.SignUpView> {
            val vm: SignUpViewModel = hiltViewModel()
            SignUpView(navController, vm, selectedCategoryViewModel)
        }

        animatedComposable<NavigationItem.OtpView> {
            val vm: OtpViewModel = hiltViewModel()
            OtpView(navController, vm, selectedCategoryViewModel)
        }
    }
}
