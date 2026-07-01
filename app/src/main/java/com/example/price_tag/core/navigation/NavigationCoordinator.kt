package com.example.price_tag.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * App-wide navigation bus.
 *
 * Any ViewModel in any feature module can inject this and emit an event
 * without knowing anything about the NavController. The NavController
 * only lives in the UI layer and listens via ObserveNavigationEvents.
 *
 * SharedFlow with replay=0 → each event is consumed exactly once;
 * no stale navigation happens on recomposition.
 */
@Singleton
class NavigationCoordinator @Inject constructor() {

    private val _events = MutableSharedFlow<NavigationEvent>(replay = 0,
                                                             extraBufferCapacity = 8   // prevents suspension on fast emitters
    )

    /** UI layer collects this. */
    val events = _events.asSharedFlow()

    /**
     * Push a destination onto the back stack.
     * Use from a ViewModel coroutine scope.
     */
    suspend fun navigate(destination: NavigationItem) {
        _events.emit(NavigationEvent.Navigate(destination))
    }

    /**
     * Pop the current destination.
     */
    suspend fun navigateBack() {
        _events.emit(NavigationEvent.NavigateBack)
    }

    /**
     * Navigate and clear the entire back stack.
     * Ideal for post-login or post-logout redirects.
     */
    suspend fun navigateAndClear(destination: NavigationItem) {
        _events.emit(NavigationEvent.NavigateAndClear(destination))
    }
}