package com.example.price_tag.ui.features.home

import androidx.lifecycle.ViewModel
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.core.navigation.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationCoordinator: NavigationCoordinator
) : ViewModel() {

    fun onProductClick(productId: Long) {
        navigationCoordinator.navigateTo(NavigationItem.ProductDetailsView(productId))
    }
    
    fun onSeeAllFeaturedClick() {
        // Example: navigate to a dedicated featured products screen
        // navigationCoordinator.navigateTo(NavigationItem.FeaturedProductsView)
    }

    fun onCartClick() {
        // navigationCoordinator.navigateTo(NavigationItem.CartView)
    }
}
