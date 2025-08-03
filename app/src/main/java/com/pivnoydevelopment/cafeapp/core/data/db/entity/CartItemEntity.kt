package com.pivnoydevelopment.cafeapp.core.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_items",
    indices = [Index(value = ["userId", "locationId", "menuItemId"], unique = true)]
)
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val locationId: Int,
    val locationName: String?,
    val menuItemId: Int,
    val menuItemName: String,
    val imageUrl: String,
    val price: Int,
    val count: Int
)