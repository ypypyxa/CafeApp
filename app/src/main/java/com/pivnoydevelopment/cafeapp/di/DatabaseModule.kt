package com.pivnoydevelopment.cafeapp.di

import android.content.Context
import androidx.room.Room
import com.pivnoydevelopment.cafeapp.core.data.db.AppDatabase
import com.pivnoydevelopment.cafeapp.core.data.db.dao.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "сart.db"
        ).build()
    }

    @Provides
    fun provideCartDao(db: AppDatabase): CartDao = db.cartDao()
}