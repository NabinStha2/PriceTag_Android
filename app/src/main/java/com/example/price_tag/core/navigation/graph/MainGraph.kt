package com.example.price_tag.core.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.core.utils.animatedComposable
import com.example.price_tag.core.utils.sharedViewModel
import com.example.price_tag.ui.components.SelectedCategoryViewModel
import com.example.price_tag.ui.features.category.CategoryViewModel
import com.example.price_tag.ui.features.main.MainView
import com.example.price_tag.ui.features.main.MainViewModel

/**
 * Main app graph — accessible only after authentication.
 *
 * ProductView accepts an optional subCategoryId (nullable Long).
 * Navigation Compose serializes nullable primitives correctly when
 * the route is @Serializable — no custom NavType needed.
 */

fun NavGraphBuilder.mainGraph(
    navController: NavController,
) {
    navigation<NavigationItem.MainGraph>(startDestination = NavigationItem.MainView) {
        animatedComposable<NavigationItem.MainView> { backStackEntry ->
            val mainVm: MainViewModel =
                backStackEntry.sharedViewModel<MainViewModel, NavigationItem.MainGraph>(navController)

            // Using clean sharedViewModel helper for graph-scoped ViewModels
            val categoryVm =
                backStackEntry.sharedViewModel<CategoryViewModel, NavigationItem.MainGraph>(navController)
            val selectedCategoryViewModel =
                backStackEntry.sharedViewModel<SelectedCategoryViewModel, NavigationItem.MainGraph>(navController)

            MainView(navController = navController as NavHostController,
                     viewModel = mainVm,
                     categoryViewModel = categoryVm)
        }
    }
}
