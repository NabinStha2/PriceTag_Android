package com.example.price_tag.data.repository.session

import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.core.state.AppState
import com.example.price_tag.domain.repository.session.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class SessionRepositoryImpl @Inject constructor(private val dataStore: DataStoreManager) :
    SessionRepository {

    override val appState: Flow<AppState> = combine(dataStore.firstTimeInApp,
                                                    dataStore.bearerToken) { firstTime, token ->
        when {
            firstTime -> AppState.OnBoarding
            token != null -> AppState.Authenticated
            else -> AppState.Unauthenticated
        }
    }

    override val bearerToken: Flow<String?>
        get() = dataStore.bearerToken

    override suspend fun saveSession(token: String,
                                     refreshToken: String,
                                     userId: Long) {
        dataStore.saveAuthTokens(token,
                                 refreshToken,
                                 userId)
    }

    override suspend fun clearSession() {
        dataStore.clearAuthTokens()
    }

    override suspend fun setFirstTime(isFirstTime: Boolean) {
        dataStore.setFirstTime(isFirstTime)

    }
}