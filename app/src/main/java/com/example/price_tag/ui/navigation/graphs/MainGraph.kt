package com.example.price_tag.ui.navigation.graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.price_tag.ui.components.SelectedCategoryViewModel
import com.example.price_tag.ui.features.category.CategoryViewModel
import com.example.price_tag.ui.features.main.MainView
import com.example.price_tag.ui.features.main.MainViewModel
import com.example.price_tag.ui.navigation.NavigationItem
import com.example.price_tag.utils.animatedComposable
import com.example.price_tag.utils.sharedViewModel
import kotlinx.serialization.Serializable

@Serializable
data object MainGraph

fun NavGraphBuilder.mainGraph(
    navController: NavController,
) {
    navigation<MainGraph>(startDestination = NavigationItem.MainView) {
        animatedComposable<NavigationItem.MainView> { backStackEntry ->
            val mainVm: MainViewModel = hiltViewModel()

            // Using clean sharedViewModel helper for graph-scoped ViewModels
            val categoryVm = backStackEntry.sharedViewModel<CategoryViewModel, MainGraph>(navController)
            val selectedCategoryViewModel = backStackEntry.sharedViewModel<SelectedCategoryViewModel, MainGraph>(navController)

            MainView(
                navController = navController as NavHostController,
                viewModel = mainVm,
                selectedCategoryViewModel = selectedCategoryViewModel,
                categoryViewModel = categoryVm
            )
        }
    }
}
