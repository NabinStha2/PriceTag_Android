package com.example.price_tag.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.price_tag.core.navigation.graph.authNavGraph
import com.example.price_tag.core.navigation.graph.mainGraph
import com.example.price_tag.core.navigation.graph.onboardingNavGraph
import com.example.price_tag.core.state.AppState

/**
 * Decides which nav graph to display based on AppState.
 *
 * Key design decisions:
 *
 * 1. The NavHost startDestination is always SplashView. DataStore is cold
 *    on first frame, so AppState starts as Loading. SplashScreen renders
 *    while the real state resolves — typically < 300ms.
 *
 * 2. When AppState changes (e.g. after login), the Composable recomposes
 *    with a NEW NavHost (different startDestination key). This is intentional
 *    — it replaces the entire nav stack cleanly without any manual popUpTo.
 *    The `key` parameter forces recomposition when appState changes.
 *
 * 3. Each nested graph has its own @Serializable graph route as the
 *    navigation() type parameter — fully type-safe, zero strings.
 */
@Composable
fun RootNavHost(
    navController: NavHostController,
    appState: AppState
) {
    val startDestination: NavigationItem = when (appState) {
        AppState.Loading         -> NavigationItem.SplashView
        AppState.OnBoarding      -> NavigationItem.OnboardingGraph
        AppState.Unauthenticated -> NavigationItem.AuthGraph
        AppState.Authenticated   -> NavigationItem.MainGraph
    }

    NavHost(
        navController    = navController,
        startDestination = startDestination
    ) {
        // Splash (shown while AppState == Loading)
        composable<NavigationItem.SplashView> {
//            SplashScreen()
        }

        // Nested graphs
        onboardingNavGraph(navController)
        authNavGraph(navController)
        mainGraph(navController)
    }
}