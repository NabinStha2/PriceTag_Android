package com.example.price_tag.ui.navigation

sealed class NavigationItem(val route: String) {
    data object SplashView : NavigationItem("splash_screen")
    data object OnboardingView : NavigationItem("onboarding_screen")
    data object MainView : NavigationItem("main_screen")
    data object LoginView : NavigationItem("login_screen")
    data object SignUpView : NavigationItem("sign_up_screen")
    data object ProductView : NavigationItem("product_screen?subCategoryId={subCategoryId}") {
        fun createRoute(subCategoryId: Long?) = "product_screen?subCategoryId=$subCategoryId"
    }

    data object ProductDetailsView : NavigationItem("product_details_screen?productId={productId}") {
        fun createRoute(productId: Long?) = "product_details_screen?productId=$productId"
    }

    data object OtpView : NavigationItem("otp_screen")
}