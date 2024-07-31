package com.example.price_tag.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.price_tag.ui.features.home.HomeView
import com.example.price_tag.ui.features.onboarding.OnboardingScreen


@Composable
fun NavigationStack() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.OnboardingScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            HomeView()
        }
        composable(
            route = Screen.OnboardingScreen.route,
        ) {
            OnboardingScreen()
        }
    }
}