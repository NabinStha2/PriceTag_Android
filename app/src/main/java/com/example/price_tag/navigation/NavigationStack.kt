package com.example.price_tag.navigation

import LoginView
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.ui.features.auth.signup.SignUpView
import com.example.price_tag.ui.features.home.HomeView
import com.example.price_tag.ui.features.onboarding.OnboardingView


@Composable
fun NavigationStack() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            HomeView()
        }
        composable(
            route = Screen.OnboardingScreen.route,
        ) {
            OnboardingView()
        }
        composable(route = Screen.LoginScreen.route){
            LoginView(navController)
        }
        composable(route = Screen.SignUpScreen.route){
            SignUpView(navController )
        }
    }
}