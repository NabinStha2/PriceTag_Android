package com.example.price_tag.core.session.data.repository

import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.core.state.AppState
import com.example.price_tag.core.session.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor(private val dataStoreManager: DataStoreManager) :
    SessionRepository {

    /**
     * Combines two DataStore streams into a single AppState.
     *
     * Decision matrix:
     * ┌────────────┬──────────────┬─────────────────┐
     * │ firstTime  │ bearerToken  │   AppState       │
     * ├────────────┼──────────────┼─────────────────┤
     * │   true     │   any        │   Onboarding     │
     * │   false    │   null/blank │   Unauthenticated│
     * │   false    │   non-blank  │   Authenticated  │
     * └────────────┴──────────────┴─────────────────┘
     *
     * The Loading state is set as the initial value in AppViewModel
     * (stateIn initialValue) so the splash screen shows while
     * DataStore cold-starts from disk.
     */
    override val appState: Flow<AppState> = combine(dataStoreManager.firstTimeInApp,
                                                    dataStoreManager.bearerToken) { isFirstTime, token ->
        when {
            isFirstTime -> AppState.OnBoarding
            token.isNullOrBlank() -> AppState.Unauthenticated
            else -> AppState.Authenticated
        }
    }

    override suspend fun saveToken(token: String) {
        dataStoreManager.saveBearerToken(token)
    }

    override suspend fun clearSession() {
        dataStoreManager.clearAuthTokens()
    }

    override suspend fun completeOnboarding() {
        dataStoreManager.setFirstTime(isFirstTime = true)
    }
}