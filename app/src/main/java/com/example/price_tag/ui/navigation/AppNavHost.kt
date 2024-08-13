package com.example.price_tag.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.ui.features.auth.login.LoginView
import com.example.price_tag.ui.features.auth.otp.OtpView
import com.example.price_tag.ui.features.auth.signup.SignUpView
import com.example.price_tag.ui.features.main.MainView
import com.example.price_tag.ui.features.onboarding.OnboardingView
import com.example.price_tag.ui.features.product.ProductView


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = NavigationItem.MainView.route,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = NavigationItem.OnboardingView.route,
        ) {
            OnboardingView(navController)
        }

        composable(
            route = NavigationItem.MainView.route,
        ) {
            MainView(navController)
        }

        composable(
            route = NavigationItem.LoginView.route,
        ) {
            LoginView(navController)
        }

        composable(
            route = NavigationItem.SignUpView.route,
        ) {
            SignUpView(navController)
        }

        composable(
            route = NavigationItem.OtpView.route,
        ) {
            OtpView(navController)
        }

        composable(
            route = NavigationItem.ProductView.route,
        ) {
            ProductView(navController)
        }
    }
}