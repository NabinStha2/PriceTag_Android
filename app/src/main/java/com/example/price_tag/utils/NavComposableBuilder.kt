package com.example.price_tag.utils

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.animatedComposable(route: String,
                                       arguments: List<NamedNavArgument> = emptyList(),
                                       content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit) {
    composable(route = route,
               arguments = arguments,
               enterTransition = { slideInFromRight() },
               exitTransition = { slideOutToLeft() },
               content = content)
}