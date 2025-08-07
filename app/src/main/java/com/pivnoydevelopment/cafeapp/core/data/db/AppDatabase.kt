package com.pivnoydevelopment.cafeapp.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pivnoydevelopment.cafeapp.core.data.db.dao.CartDao
import com.pivnoydevelopment.cafeapp.core.data.db.entity.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}