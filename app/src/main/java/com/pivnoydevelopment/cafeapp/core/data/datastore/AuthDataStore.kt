package com.pivnoydevelopment.cafeapp.core.data.datastore

import com.pivnoydevelopment.cafeapp.core.domain.model.AuthData
import kotlinx.coroutines.flow.Flow

interface AuthDataStore {
    val authDataFlow: Flow<AuthData?>
    suspend fun saveAuthData(authData: AuthData)
    suspend fun clear()
}