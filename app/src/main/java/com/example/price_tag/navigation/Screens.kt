package com.example.price_tag.navigation

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splash_screen")
    data object OnboardingScreen : Screen("onboarding_screen")
}