package com.example.price_tag.domain.repository.session

import com.example.price_tag.core.state.AppState
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    val appState: Flow<AppState>

    val bearerToken: Flow<String?>

    suspend fun saveSession(token: String,
                            refreshToken: String,
                            userId: Long)

    suspend fun clearSession()

    suspend fun setFirstTime(isFirstTime: Boolean)
}