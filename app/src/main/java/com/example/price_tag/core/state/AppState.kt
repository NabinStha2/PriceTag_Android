package com.example.price_tag.core.state

sealed interface AppState {
    data object Loading : AppState
    data object OnBoarding : AppState
    data object Unauthenticated : AppState
    data object Authenticated : AppState
}