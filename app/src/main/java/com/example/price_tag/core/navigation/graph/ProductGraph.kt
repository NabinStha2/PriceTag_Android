package com.example.price_tag.core.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.core.utils.animatedComposable
import com.example.price_tag.core.utils.sharedViewModel
import com.example.price_tag.ui.components.SelectedCategoryViewModel
import com.example.price_tag.ui.features.product.ProductView
import com.example.price_tag.ui.features.product.ProductViewModel

fun NavGraphBuilder.productGraph(navController: NavController) {
    navigation<NavigationItem.ProductGraph>(
        startDestination = NavigationItem.ProductView(
            null,
            null,
        ),
    ) {
        animatedComposable<NavigationItem.ProductView> { backStackEntry ->
            val productViewModel: ProductViewModel =
                backStackEntry.sharedViewModel<ProductViewModel, NavigationItem.ProductGraph>(navController)
            val selectedCategoryViewModel =
                backStackEntry.sharedViewModel<SelectedCategoryViewModel, NavigationItem.MainGraph>(navController)

            val args = backStackEntry.toRoute<NavigationItem.ProductView>()

            ProductView(
                navController = navController as NavHostController,
                paddingValues = PaddingValues(0.dp),
                productViewModel = productViewModel,
                selectedCategoryViewModel = selectedCategoryViewModel, //                subCategoryId = backStackEntry.arguments?.getLong("subCategoryId"),
                subCategoryId = args.subCategoryId,
                subCategoryName = args.subCategoryName,
            )

        }

    }
}