package com.pivnoydevelopment.cafeapp.features.auth.data.datastore

import com.pivnoydevelopment.cafeapp.features.auth.domain.model.AuthData
import kotlinx.coroutines.flow.Flow

interface AuthDataStore {
    val authDataFlow: Flow<AuthData?>
    suspend fun saveAuthData(authData: AuthData)
    suspend fun clear()
}