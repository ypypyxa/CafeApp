package com.pivnoydevelopment.cafeapp.di

import com.pivnoydevelopment.cafeapp.core.data.db.dao.CartDao
import com.pivnoydevelopment.cafeapp.core.data.db.impl.CartRepositoryImpl
import com.pivnoydevelopment.cafeapp.core.data.network.NetworkClient
import com.pivnoydevelopment.cafeapp.core.data.network.impl.CoffeeRepositoryImpl
import com.pivnoydevelopment.cafeapp.core.domain.db.api.CartRepository
import com.pivnoydevelopment.cafeapp.core.domain.network.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.core.util.ResourceProvider
import com.pivnoydevelopment.cafeapp.core.util.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCoffeeRepository(
        apiService: NetworkClient,
        resourceProvider: ResourceProvider
    ): CoffeeRepository {
        return CoffeeRepositoryImpl(apiService, resourceProvider)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        dao: CartDao,
        sessionManager: SessionManager,
        resourceProvider: ResourceProvider
    ): CartRepository {
        return CartRepositoryImpl(dao, sessionManager, resourceProvider)
    }
}