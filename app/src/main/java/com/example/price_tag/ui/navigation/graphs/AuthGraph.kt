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
import com.example.price_tag.utils.sharedViewModel
import kotlinx.serialization.Serializable

@Serializable
data object AuthGraph

fun NavGraphBuilder.authGraph(
    navController: NavController,
) {
    navigation<AuthGraph>(startDestination = NavigationItem.LoginView) {
        animatedComposable<NavigationItem.LoginView> {
            val vm: LoginViewModel = hiltViewModel()
            LoginView(navController, vm)
        }

        animatedComposable<NavigationItem.SignUpView> { backStackEntry ->
            val vm: SignUpViewModel = hiltViewModel()
            // Using clean sharedViewModel helper
            val selectedCategoryViewModel = backStackEntry.sharedViewModel<SelectedCategoryViewModel, AuthGraph>(navController)
            
            SignUpView(navController, vm, selectedCategoryViewModel)
        }

        animatedComposable<NavigationItem.OtpView> { backStackEntry ->
            val vm: OtpViewModel = hiltViewModel()
            // Using clean sharedViewModel helper
            val selectedCategoryViewModel = backStackEntry.sharedViewModel<SelectedCategoryViewModel, AuthGraph>(navController)

            OtpView(navController, vm, selectedCategoryViewModel)
        }
    }
}
