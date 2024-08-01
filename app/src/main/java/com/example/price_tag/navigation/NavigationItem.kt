package com.example.price_tag.navigation

sealed class NavigationItem(val route: String) {
    data object SplashView : NavigationItem("splash_screen")
    data object OnboardingView : NavigationItem("onboarding_screen")
    data object MainView : NavigationItem("main_screen")
}