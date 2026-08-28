package com.example.price_tag.core.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.core.utils.animatedComposable
import com.example.price_tag.feature.checkout.presentation.CheckoutView

fun NavGraphBuilder.checkoutGraph(navController: NavController) {
    navigation<NavigationItem.CheckoutGraph>(
        startDestination = NavigationItem.CheckoutView(
            cartId = 0L,
        ),
    ) {
        animatedComposable<NavigationItem.CheckoutView> { backStackEntry ->
            val args = backStackEntry.toRoute<NavigationItem.CheckoutView>()

            CheckoutView(
                cartId = args.cartId,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}