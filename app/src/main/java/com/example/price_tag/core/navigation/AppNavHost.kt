package com.example.price_tag.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.price_tag.ui.features.onboarding.OnboardingView
import com.example.price_tag.core.navigation.graph.authGraph
import com.example.price_tag.core.navigation.graph.mainGraph
import com.example.price_tag.core.navigation.graph.productGraph
import com.example.price_tag.core.utils.animatedComposable
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Any,
    navigationCoordinator: NavigationCoordinator,
) {
    LaunchedEffect(Unit) {
        navigationCoordinator.navigationEvents.collectLatest { event ->
            when (event) {
                is NavEvent.Navigate -> {
                    navController.navigate(event.destination,
                                           event.builder)
                }

                is NavEvent.Back -> {
                    navController.popBackStack()
                }

                is NavEvent.PopTo -> {
                    navController.popBackStack(event.destination,
                                               event.inclusive,
                                               event.saveState)
                }
            }
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {

        animatedComposable<NavigationItem.OnboardingView> {
            OnboardingView(navController)
        }

        // Feature Graphs
        authGraph(navController)
        mainGraph(navController)
        productGraph(navController)
    }
}
