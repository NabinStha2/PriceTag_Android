package com.example.price_tag.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.price_tag.ui.components.SelectedCategoryViewModel
import com.example.price_tag.ui.features.onboarding.OnboardingView
import com.example.price_tag.ui.navigation.graphs.MainGraph
import com.example.price_tag.ui.navigation.graphs.authGraph
import com.example.price_tag.ui.navigation.graphs.mainGraph
import com.example.price_tag.ui.navigation.graphs.productGraph
import com.example.price_tag.utils.animatedComposable
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Any,
    selectedCategoryViewModel: SelectedCategoryViewModel,
    navigationCoordinator: NavigationCoordinator,
) {
    LaunchedEffect(Unit) {
        navigationCoordinator.navigationEvents.collectLatest { event ->
            when (event) {
                is NavEvent.Navigate -> {
                    navController.navigate(event.destination, event.builder)
                }
                is NavEvent.Back -> {
                    navController.popBackStack()
                }
                is NavEvent.PopTo -> {
                    navController.popBackStack(event.destination, event.inclusive, event.saveState)
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
        authGraph(navController, selectedCategoryViewModel)
        mainGraph(navController, selectedCategoryViewModel)
        productGraph(navController, selectedCategoryViewModel)
    }
}
