package com.pivnoydevelopment.cafeapp.di

import com.pivnoydevelopment.cafeapp.core.domain.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.features.locations.domain.impl.GetLocationsUseCaseImpl
import com.pivnoydevelopment.cafeapp.features.menu.domain.impl.GetMenuUseCaseImpl
import com.pivnoydevelopment.cafeapp.features.auth.domain.impl.LoginUseCaseImpl
import com.pivnoydevelopment.cafeapp.features.auth.domain.impl.RegisterUseCaseImpl
import com.pivnoydevelopment.cafeapp.features.locations.domain.usecase.GetLocationsUseCase
import com.pivnoydevelopment.cafeapp.features.menu.domain.usecase.GetMenuUseCase
import com.pivnoydevelopment.cafeapp.features.auth.domain.usecase.LoginUseCase
import com.pivnoydevelopment.cafeapp.features.auth.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(coffeeRepository: CoffeeRepository): LoginUseCase =
        LoginUseCaseImpl(coffeeRepository)

    @Provides
    @Singleton
    fun provideRegisterUseCase(coffeeRepository: CoffeeRepository): RegisterUseCase =
        RegisterUseCaseImpl(coffeeRepository)

    @Provides
    @Singleton
    fun provideGetLocationsUseCase(coffeeRepository: CoffeeRepository): GetLocationsUseCase =
        GetLocationsUseCaseImpl(coffeeRepository)

    @Provides
    @Singleton
    fun provideGetMenuUseCase(coffeeRepository: CoffeeRepository): GetMenuUseCase =
        GetMenuUseCaseImpl(coffeeRepository)
}