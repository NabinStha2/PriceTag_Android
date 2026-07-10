package com.example.price_tag.core.navigation

import kotlinx.serialization.Serializable

/**
 * Sealed interface representing every destination in the app.
 *
 * Rules:
 * - data object  → no arguments (singleton destination)
 * - data class   → has arguments (Navigation serializes them via @Serializable)
 *
 * Navigation Compose 2.8+ uses composable<T>() and toRoute<T>()
 * so there are zero string routes anywhere in this codebase.
 */
sealed interface NavigationItem {

    // ── Splash ──────────────────────────────────────────────────────────────

    @Serializable
    data object SplashView : NavigationItem

    // ── Onboarding ──────────────────────────────────────────────────────────

    @Serializable
    data object OnboardingView : NavigationItem

    // ── Auth ────────────────────────────────────────────────────────────────

    @Serializable
    data object LoginView : NavigationItem

    @Serializable
    data object SignUpView : NavigationItem

    /**
     * @param phone The phone number to display masked on the OTP screen.
     */
    @Serializable
    data class OtpView(val phone: String) : NavigationItem

    // ── Main ────────────────────────────────────────────────────────────────

    @Serializable
    data object MainView : NavigationItem

    @Serializable
    data object HomeView : NavigationItem

    /**
     * @param subCategoryId Optional filter; null means show all products.
     */
    @Serializable
    data class ProductView(
        val subCategoryId: Long? = null,
        val subCategoryName: String? = null,
    ) : NavigationItem

    /**
     * @param productId The ID of the product to display.
     */
    @Serializable
    data class ProductDetailsView(val productId: Long) : NavigationItem

    @Serializable
    data class RatingReviewView(val productId: Long) : NavigationItem

    // ── Graph roots (not real screens; used as nested graph start routes) ───

    @Serializable
    data object AuthGraph : NavigationItem

    @Serializable
    data object MainGraph : NavigationItem

    @Serializable
    data object OnboardingGraph : NavigationItem

    @Serializable
    data object ProductGraph : NavigationItem

}