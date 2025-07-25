package com.pivnoydevelopment.cafeapp.core.data.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pivnoydevelopment.cafeapp.core.data.datastore.AuthDataStore
import com.pivnoydevelopment.cafeapp.core.domain.model.AuthData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AuthDataStore {

    companion object {
        val TOKEN_KEY = stringPreferencesKey("token")
        val TOKEN_LIFETIME_KEY = longPreferencesKey("token_lifetime")
        val TOKEN_SAVED_AT_KEY = longPreferencesKey("token_saved_at")
    }

    override val authDataFlow: Flow<AuthData?> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs ->
            val token = prefs[TOKEN_KEY]
            val tokenLifetime = prefs[TOKEN_LIFETIME_KEY]
            val savedAt = prefs[TOKEN_SAVED_AT_KEY]

            if (token != null && tokenLifetime != null && savedAt != null) {
                AuthData(token, tokenLifetime, savedAt)
            } else {
                null
            }
        }

    override suspend fun saveAuthData(authData: AuthData) {
        dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = authData.token
            prefs[TOKEN_LIFETIME_KEY] = authData.tokenLifeTime
            prefs[TOKEN_SAVED_AT_KEY] = authData.savedAt
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}