package com.example.price_tag.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
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
    startDestination: String = NavigationItem.LoginView.route,
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

        composable(route = NavigationItem.MainView.route, enterTransition = {
            slideIn(tween(durationMillis = 300)) {
                IntOffset(x = it.width, y = 0) // Start off-screen to the right
            }
        }, exitTransition = {
            slideOut(tween(durationMillis = 300)) {
                IntOffset(x = -it.width, y = 0) // Exit off-screen to the left
            }
        }) {
            MainView(navController)
        }

        composable(route = NavigationItem.LoginView.route, enterTransition = {
            slideIn(tween(durationMillis = 300)) {
                IntOffset(x = it.width, y = 0) // Start off-screen to the right
            }
        }, exitTransition = {
            slideOut(tween(durationMillis = 300)) {
                IntOffset(x = -it.width, y = 0) // Exit off-screen to the left
            }
        }) {
            LoginView(navController)
        }

        composable(route = NavigationItem.SignUpView.route, enterTransition = {
            slideIn(tween(durationMillis = 300)) {
                IntOffset(x = it.width, y = 0) // Start off-screen to the right
            }
        }, exitTransition = {
            slideOut(tween(durationMillis = 300)) {
                IntOffset(x = -it.width, y = 0) // Exit off-screen to the left
            }
        }) {
            SignUpView(navController)
        }

        composable(route = NavigationItem.OtpView.route, enterTransition = {
            slideIn(tween(durationMillis = 300)) {
                IntOffset(x = it.width, y = 0) // Start off-screen to the right
            }
        }, exitTransition = {
            slideOut(tween(durationMillis = 300)) {
                IntOffset(x = -it.width, y = 0) // Exit off-screen to the left
            }
        }) {
            OtpView(navController)
        }

        composable(route = NavigationItem.ProductView.route, enterTransition = {
            slideIn(tween(durationMillis = 300)) {
                IntOffset(x = it.width, y = 0) // Start off-screen to the right
            }
        }, exitTransition = {
            slideOut(tween(durationMillis = 300)) {
                IntOffset(x = -it.width, y = 0) // Exit off-screen to the left
            }
        }) {
            ProductView(navController)
        }
    }
}