package com.example.price_tag.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest

/**
 * Collects NavigationCoordinator events and drives the NavController.
 *
 * Placed once at the root of the composition (inside MainActivity setContent).
 * Uses collectLatest so if two events arrive back-to-back only the last
 * one is processed — prevents double-navigation on rapid taps.
 *
 * NavigateAndClear uses popUpTo(0) with inclusive=true to wipe the
 * entire back stack before pushing the new destination, making it
 * impossible to navigate back to login/onboarding after authentication.
 */
@Composable
fun ObserveNavigationEvents(
    coordinator: NavigationCoordinator,
    navController: NavHostController
) {
    LaunchedEffect(coordinator, navController) {
        coordinator.events.collectLatest { event ->
            when (event) {
                is NavigationEvent.Navigate -> {
                    navController.navigate(event.destination)
                }

                is NavigationEvent.NavigateBack -> {
                    navController.popBackStack()
                }

                is NavigationEvent.NavigateAndClear -> {
                    navController.navigate(event.destination) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}