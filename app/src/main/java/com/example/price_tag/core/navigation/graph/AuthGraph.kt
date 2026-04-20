package com.example.price_tag.core.navigation.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.core.utils.animatedComposable
import com.example.price_tag.core.utils.sharedViewModel
import com.example.price_tag.ui.components.SelectedCategoryViewModel
import com.example.price_tag.ui.features.auth.login.LoginView
import com.example.price_tag.ui.features.auth.login.LoginViewModel
import com.example.price_tag.ui.features.auth.otp.OtpView
import com.example.price_tag.ui.features.auth.otp.OtpViewModel
import com.example.price_tag.ui.features.auth.signup.SignUpView
import com.example.price_tag.ui.features.auth.signup.SignUpViewModel

fun NavGraphBuilder.authGraph(
    navController: NavController,
) {
    navigation<NavigationItem.AuthGraph>(startDestination = NavigationItem.LoginView) {
        animatedComposable<NavigationItem.LoginView> { backStackEntry ->
            val vm: LoginViewModel =
                backStackEntry.sharedViewModel<LoginViewModel, NavigationItem.AuthGraph>(navController)
            LoginView(navController,
                      vm)
        }

        animatedComposable<NavigationItem.SignUpView> { backStackEntry ->
            val vm: SignUpViewModel = hiltViewModel() // Using clean sharedViewModel helper
            val selectedCategoryViewModel =
                backStackEntry.sharedViewModel<SelectedCategoryViewModel, NavigationItem.AuthGraph>(navController)

            SignUpView(navController,
                       vm,
                       selectedCategoryViewModel)
        }

        animatedComposable<NavigationItem.OtpView> { backStackEntry ->
            val vm: OtpViewModel =
                backStackEntry.sharedViewModel<OtpViewModel, NavigationItem.AuthGraph>(navController) // Using clean sharedViewModel helper
            val selectedCategoryViewModel =
                backStackEntry.sharedViewModel<SelectedCategoryViewModel, NavigationItem.AuthGraph>(navController)

            OtpView(navController,
                    vm,
                    selectedCategoryViewModel)
        }
    }
}
