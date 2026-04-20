package com.example.price_tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.core.state.AppState
import com.example.price_tag.domain.repository.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Lives at the Activity level (single instance for the whole app).
 *
 * stateIn config:
 * - SharingStarted.WhileSubscribed(5_000) → keeps the upstream Flow alive
 *   for 5 seconds after the last subscriber disappears (survives config change)
 * - initialValue = AppState.Loading → renders SplashScreen while DataStore
 *   reads from disk on the first cold start
 */
@HiltViewModel
class AppViewModel @Inject constructor(sessionRepository: SessionRepository) : ViewModel() {

    val appState: StateFlow<AppState> = sessionRepository.appState.stateIn(scope = viewModelScope,
                                                                           started = SharingStarted.WhileSubscribed(5_000L),
                                                                           initialValue = AppState.Loading)
}