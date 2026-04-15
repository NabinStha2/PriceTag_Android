package com.example.price_tag.ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.price_tag.ui.components.SelectedCategoryViewModel
import com.example.price_tag.ui.features.product.ProductView
import com.example.price_tag.ui.features.product.ProductViewModel
import com.example.price_tag.ui.features.productDetails.ProductDetailsView
import com.example.price_tag.ui.features.productDetails.ProductDetailsViewModel
import com.example.price_tag.ui.navigation.NavigationItem
import com.example.price_tag.utils.animatedComposable
import kotlinx.serialization.Serializable

@Serializable
data object ProductGraph

fun NavGraphBuilder.productGraph(
    navController: NavController,
    selectedCategoryViewModel: SelectedCategoryViewModel
) {
    navigation<ProductGraph>(startDestination = NavigationItem.ProductView(null)) {
        animatedComposable<NavigationItem.ProductView> { backStackEntry ->
            val vm: ProductViewModel = hiltViewModel()
            val destination = backStackEntry.toRoute<NavigationItem.ProductView>()

            ProductView(
                navController = navController,
                subCategoryId = destination.subCategoryId,
                selectedCategoryViewModel = selectedCategoryViewModel,
                productViewModel = vm,
                paddingValues = PaddingValues(),
            )
        }

        animatedComposable<NavigationItem.ProductDetailsView> { backStackEntry ->
            val vm: ProductDetailsViewModel = hiltViewModel()
            val destination = backStackEntry.toRoute<NavigationItem.ProductDetailsView>()

            ProductDetailsView(
                navController = navController,
                productId = destination.productId,
                selectedCategoryViewModel = selectedCategoryViewModel,
                productDetailsViewModel = vm,
                paddingValues = PaddingValues(),
            )
        }
    }
}
