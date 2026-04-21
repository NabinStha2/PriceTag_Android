package com.example.price_tag.core.navigation.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import androidx.navigation.toRoute
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

/**
 * Auth graph with a graph-scoped AuthViewModel.
 *
 * sharedViewModel() resolves the ViewModel from the graph's back stack entry,
 * so Login, SignUp, and Otp all share the exact same AuthViewModel instance.
 * It is cleared automatically when the auth graph is popped.
 */
fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation<NavigationItem.AuthGraph>(startDestination = NavigationItem.LoginView) {
        animatedComposable<NavigationItem.LoginView> { backStackEntry ->
            val loginViewModel: LoginViewModel =
                backStackEntry.sharedViewModel<LoginViewModel, NavigationItem.AuthGraph>(navController)
            LoginView(navController,
                      loginViewModel)
        }

        animatedComposable<NavigationItem.SignUpView> { backStackEntry ->
            val signUpViewModel: SignUpViewModel =
                backStackEntry.sharedViewModel<SignUpViewModel, NavigationItem.AuthGraph>(navController)
            val selectedCategoryViewModel =
                backStackEntry.sharedViewModel<SelectedCategoryViewModel, NavigationItem.AuthGraph>(navController)
            SignUpView(navController,
                       signUpViewModel,
                       selectedCategoryViewModel)
        }

        animatedComposable<NavigationItem.OtpView> { backStackEntry ->
            val route = backStackEntry.toRoute<NavigationItem.OtpView>()
            val otpViewModel: OtpViewModel =
                backStackEntry.sharedViewModel<OtpViewModel, NavigationItem.AuthGraph>(navController)
            val selectedCategoryViewModel =
                backStackEntry.sharedViewModel<SelectedCategoryViewModel, NavigationItem.AuthGraph>(navController)
            OtpView(navController = navController,
                    otpViewModel = otpViewModel,
                    selectedCategoryViewModel = selectedCategoryViewModel)
        }
    }
}
