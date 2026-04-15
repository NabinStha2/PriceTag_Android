package com.example.price_tag.ui.navigation

import androidx.navigation.NavOptionsBuilder

sealed class NavEvent {
    data class Navigate(
        val destination: Any,
        val builder: NavOptionsBuilder.() -> Unit = {}
    ) : NavEvent()

    data object Back : NavEvent()

    data class PopTo(
        val destination: Any,
        val inclusive: Boolean = false,
        val saveState: Boolean = false
    ) : NavEvent()
}
