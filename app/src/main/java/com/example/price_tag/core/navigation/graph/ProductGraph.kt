package com.example.price_tag.core.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.core.ui.viewmodel.SelectedCategoryViewModel
import com.example.price_tag.core.utils.animatedComposable
import com.example.price_tag.core.utils.sharedViewModel
import com.example.price_tag.feature.cart.presentation.CartViewModel
import com.example.price_tag.feature.product.presentation.details.ProductDetailsView
import com.example.price_tag.feature.product.presentation.details.ProductDetailsViewModel
import com.example.price_tag.feature.product.presentation.listing.ProductView
import com.example.price_tag.feature.product.presentation.listing.ProductViewModel
import com.example.price_tag.feature.product.presentation.reviews.RatingReviewView

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
                subCategoryId = args.subCategoryId, //                subCategoryName = args.subCategoryName,
            )
        }

        animatedComposable<NavigationItem.ProductDetailsView> { backStackEntry ->
            val productDetailsViewModel: ProductDetailsViewModel =
                backStackEntry.sharedViewModel<ProductDetailsViewModel, NavigationItem.ProductGraph>(navController)
            val cartViewModel: CartViewModel =
                backStackEntry.sharedViewModel<CartViewModel, NavigationItem.AuthenticatedGraph>(navController)
            val selectedCategoryViewModel =
                backStackEntry.sharedViewModel<SelectedCategoryViewModel, NavigationItem.MainGraph>(navController)

            val args = backStackEntry.toRoute<NavigationItem.ProductDetailsView>()

            ProductDetailsView(
                navController = navController,
                paddingValues = PaddingValues(0.dp),
                productDetailsViewModel = productDetailsViewModel,
                productId = args.productId,
                cartViewModel = cartViewModel,
                selectedCategoryViewModel = selectedCategoryViewModel,
            )
        }

        animatedComposable<NavigationItem.RatingReviewView> { backStackEntry ->
            val productDetailsViewModel: ProductDetailsViewModel =
                backStackEntry.sharedViewModel<ProductDetailsViewModel, NavigationItem.ProductGraph>(navController)

            val args = backStackEntry.toRoute<NavigationItem.RatingReviewView>()

            RatingReviewView(
                navController = navController,
                productDetailsViewModel = productDetailsViewModel,
                productId = args.productId,
            )
        }

    }
}