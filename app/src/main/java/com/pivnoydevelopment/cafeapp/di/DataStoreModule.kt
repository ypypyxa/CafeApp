package com.pivnoydevelopment.cafeapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.pivnoydevelopment.cafeapp.features.auth.data.datastore.AuthDataStore
import com.pivnoydevelopment.cafeapp.features.auth.data.impl.AuthDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("auth_data_store") }
        )

    @Provides
    @Singleton
    fun provideAuthDataStore(dataStore: DataStore<Preferences>): AuthDataStore =
        AuthDataStoreImpl(dataStore)
}