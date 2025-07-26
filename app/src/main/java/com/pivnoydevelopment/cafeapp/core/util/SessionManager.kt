package com.pivnoydevelopment.cafeapp.core.util

import com.pivnoydevelopment.cafeapp.features.auth.data.datastore.AuthDataStore
import com.pivnoydevelopment.cafeapp.features.auth.domain.model.AuthData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val authDataStore: AuthDataStore
) {

    val isLoggedInFlow: Flow<Boolean> = authDataStore.authDataFlow.map { authData ->
        if (authData == null) return@map false

        val now = System.currentTimeMillis()
        val expiryTime = authData.savedAt + authData.tokenLifeTime
        now < expiryTime
    }

    val tokenFlow: Flow<String?> = authDataStore.authDataFlow.map { authData ->
        val now = System.currentTimeMillis()
        val isValid = authData != null && (authData.savedAt + authData.tokenLifeTime > now)
        if (isValid) authData.token else null
    }

    suspend fun getTokenOrNull(): String? {
        val authData = authDataStore.authDataFlow.firstOrNull()
        if (authData != null) {
            val now = System.currentTimeMillis()
            val expiry = authData.savedAt + authData.tokenLifeTime
            return if (now < expiry) authData.token else null
        }
        return null
    }

    suspend fun saveAuthData(authData: AuthData) {
        authDataStore.saveAuthData(authData)
    }

    suspend fun logout() {
        authDataStore.clear()
    }
}