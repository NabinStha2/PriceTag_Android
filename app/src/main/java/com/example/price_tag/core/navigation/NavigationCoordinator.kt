package com.example.price_tag.core.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationCoordinator @Inject constructor() {
    private val _navigationEvents = Channel<NavEvent>(Channel.BUFFERED)
    val navigationEvents = _navigationEvents.receiveAsFlow()

    fun navigate(event: NavEvent) {
        _navigationEvents.trySend(event)
    }

    fun navigateBack() {
        _navigationEvents.trySend(NavEvent.Back)
    }

    fun navigateTo(destination: Any) {
        _navigationEvents.trySend(NavEvent.Navigate(destination))
    }
}
