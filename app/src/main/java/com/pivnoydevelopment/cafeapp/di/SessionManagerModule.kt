package com.pivnoydevelopment.cafeapp.di

import com.pivnoydevelopment.cafeapp.core.util.SessionManager
import com.pivnoydevelopment.cafeapp.features.auth.data.datastore.AuthDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionManagerModule {

    @Provides
    @Singleton
    fun provideSessionManager(authDataStore: AuthDataStore): SessionManager {
        return SessionManager(authDataStore)
    }
}