package com.example.price_tag.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

/**
 * A clean helper to get a Hilt ViewModel scoped to a specific navigation graph.
 * This prevents duplicating 'getBackStackEntry' logic across multiple screens.
 */
@Composable
inline fun <reified T : ViewModel, reified NavTarget : Any> NavBackStackEntry.sharedViewModel(
    navController: NavController
): T {
    val navGraphRoute = remember(this) {
        navController.getBackStackEntry<NavTarget>()
    }
    return hiltViewModel(navGraphRoute)
}
