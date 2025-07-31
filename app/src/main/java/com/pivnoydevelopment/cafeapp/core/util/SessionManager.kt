package com.pivnoydevelopment.cafeapp.core.util

import com.pivnoydevelopment.cafeapp.features.auth.data.datastore.AuthDataStore
import com.pivnoydevelopment.cafeapp.features.auth.domain.model.AuthData
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val authDataStore: AuthDataStore
) {

    suspend fun getTokenOrNull(): String? {
        val authData = authDataStore.authDataFlow.firstOrNull()
        if (authData != null) {
            val now = System.currentTimeMillis()
            if (authData.tokenLifeTime != 0L) {
                val expiry = authData.savedAt + authData.tokenLifeTime
                return if (now < expiry) authData.token else null
            } else return authData.token
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