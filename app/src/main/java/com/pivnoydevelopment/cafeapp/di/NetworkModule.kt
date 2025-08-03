package com.pivnoydevelopment.cafeapp.di

import android.content.Context
import com.pivnoydevelopment.cafeapp.core.data.network.CoffeeApiService
import com.pivnoydevelopment.cafeapp.core.data.network.NetworkClient
import com.pivnoydevelopment.cafeapp.core.data.network.RetrofitNetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl(): String = "http://212.41.30.90:35005"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CoffeeApiService =
        retrofit.create(CoffeeApiService::class.java)

    @Provides
    @Singleton
    fun provideNetworkClient(
        apiService: CoffeeApiService,
        @ApplicationContext context: Context
    ): NetworkClient {
        return RetrofitNetworkClient(apiService, context)
    }
}