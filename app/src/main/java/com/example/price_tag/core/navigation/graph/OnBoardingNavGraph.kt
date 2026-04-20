package com.example.price_tag.core.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.ui.features.onboarding.OnboardingView

/**
 * Single-screen graph.
 * OnboardingScreen calls sessionRepository.completeOnboarding()
 * which flips the DataStore flag → AppState changes to Unauthenticated
 * → RootNavHost automatically switches to AuthNavGraph.
 * No explicit navigation call needed here — it's fully reactive.
 */
fun NavGraphBuilder.onboardingNavGraph(navController: NavHostController) {
    navigation<NavigationItem.OnboardingGraph>(startDestination = NavigationItem.OnboardingView) {
        composable<NavigationItem.OnboardingView> {
            OnboardingView(navController)
        }
    }
}