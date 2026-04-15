package com.example.price_tag.ui.navigation.graphs

import androidx.compose.runtime.remember
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
import kotlinx.serialization.Serializable

@Serializable
data object MainGraph

fun NavGraphBuilder.mainGraph(
    navController: NavController,
    selectedCategoryViewModel: SelectedCategoryViewModel
) {
    navigation<MainGraph>(startDestination = NavigationItem.MainView) {
        animatedComposable<NavigationItem.MainView> { backStackEntry ->
            // MainViewModel is scoped to the individual screen
            val mainVm: MainViewModel = hiltViewModel()
            
            // CategoryViewModel is scoped to the entire Main FEATURE (MainGraph)
            // This is the enterprise-standard way to share state across a business feature
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<MainGraph>()
            }
            val categoryVm: CategoryViewModel = hiltViewModel(parentEntry)

            MainView(
                navController = navController as NavHostController,
                viewModel = mainVm,
                selectedCategoryViewModel = selectedCategoryViewModel,
                categoryViewModel = categoryVm
            )
        }
    }
}
