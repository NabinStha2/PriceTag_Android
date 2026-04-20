package com.example.price_tag.core.navigation

/**
 * One-shot navigation commands emitted by any ViewModel or UseCase
 * and consumed by ObserveNavigationEvents.
 *
 * Navigate(dest)       → push destination onto the back stack
 * NavigateBack         → pop the current destination
 * NavigateAndClear(dest) → navigate and wipe the entire back stack
 *                          (used after login / logout to prevent back-navigation
 *                          into auth or onboarding screens)
 */
sealed interface NavigationEvent {
    data class Navigate(val destination: NavigationItem) : NavigationEvent
    data object NavigateBack : NavigationEvent
    data class NavigateAndClear(val destination: NavigationItem) : NavigationEvent
}