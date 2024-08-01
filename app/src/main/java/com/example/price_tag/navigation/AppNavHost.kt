package com.example.price_tag.navigation

import LoginView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.price_tag.ui.features.auth.signup.SignUpView
import com.example.price_tag.ui.features.home.HomeView
import com.example.price_tag.ui.features.main.MainView
import com.example.price_tag.ui.features.onboarding.OnboardingView


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination : String = NavigationItem.OnboardingView.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(route = NavigationItem.SplashView.route) {
            HomeView()
        }
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
    }
}