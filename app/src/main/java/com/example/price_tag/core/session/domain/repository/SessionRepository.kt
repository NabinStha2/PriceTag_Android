package com.example.price_tag.core.session.domain.repository

import com.example.price_tag.core.state.AppState
import kotlinx.coroutines.flow.Flow

/**
 * Contract for session management.
 * Lives in the domain layer — no Android/DataStore imports here.
 */
interface SessionRepository {

    /** Reactive stream of the current AppState. */
    val appState: Flow<AppState>

    /** Persist the bearer token after successful login/OTP verification. */
    suspend fun saveToken(token: String)

    /** Clear token and mark session as expired. */
    suspend fun clearSession()

    /** Mark onboarding as completed so it never shows again. */
    suspend fun completeOnboarding()
}