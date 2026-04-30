package com.example.price_tag.core.datastore


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    // 🔑 KEYS
    private object Keys {
        val FIRST_TIME = booleanPreferencesKey("first_time_in_app")
        val BEARER_TOKEN = stringPreferencesKey("bearer_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val USER_ID = longPreferencesKey("user_id")
    }

    // ---------------------------
    // 🔁 READERS (Flow based)
    // ---------------------------

    val firstTimeInApp: Flow<Boolean> =
        dataStore.data.catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .map { prefs -> prefs[Keys.FIRST_TIME] ?: true }

    val bearerToken: Flow<String?> = dataStore.data.map { prefs -> prefs[Keys.BEARER_TOKEN] }

    val refreshToken: Flow<String?> = dataStore.data.map { prefs -> prefs[Keys.REFRESH_TOKEN] }

    val userId: Flow<Long?> = dataStore.data.map { prefs -> prefs[Keys.USER_ID] }


    // ---------------------------
    // 💾 WRITERS
    // ---------------------------

    suspend fun setFirstTime(isFirstTime: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.FIRST_TIME] = isFirstTime
        }
    }

    suspend fun saveAuthTokens(accessToken: String,
                               refreshToken: String,
                               userId: Long) {
        dataStore.edit { prefs ->
            prefs[Keys.BEARER_TOKEN] = accessToken
            prefs[Keys.REFRESH_TOKEN] = refreshToken
            prefs[Keys.USER_ID] = userId
        }
    }

    suspend fun saveBearerToken(token: String) {
        dataStore.edit { prefs ->
            prefs[Keys.BEARER_TOKEN] = token
        }
    }

    suspend fun saveRefreshToken(token: String) {
        dataStore.edit { prefs ->
            prefs[Keys.REFRESH_TOKEN] = token
        }
    }

    suspend fun clearAuthTokens() {
        dataStore.edit { prefs ->
            prefs.remove(Keys.BEARER_TOKEN)
            prefs.remove(Keys.REFRESH_TOKEN)
            prefs.remove(Keys.USER_ID)
        }
    }

    suspend fun clearAll() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    suspend fun remove(key: Preferences.Key<*>) {
        dataStore.edit { prefs ->
            prefs.remove(key)
        }
    }
}