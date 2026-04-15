package com.example.price_tag.ui.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationItem {
    @Serializable
    data object SplashView : NavigationItem

    @Serializable
    data object OnboardingView : NavigationItem

    @Serializable
    data object MainView : NavigationItem

    @Serializable
    data object LoginView : NavigationItem

    @Serializable
    data object SignUpView : NavigationItem

    @Serializable
    data class ProductView(val subCategoryId: Long?) : NavigationItem

    @Serializable
    data class ProductDetailsView(val productId: Long?) : NavigationItem

    @Serializable
    data object OtpView : NavigationItem
}