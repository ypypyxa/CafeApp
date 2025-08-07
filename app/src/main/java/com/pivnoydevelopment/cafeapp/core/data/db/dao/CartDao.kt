package com.pivnoydevelopment.cafeapp.core.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pivnoydevelopment.cafeapp.core.data.db.entity.CartItemEntity

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(cartItem: CartItemEntity)

    @Query("""
        UPDATE cart_items 
        SET count = :count 
        WHERE userId = :userId 
        AND locationId = :locationId 
        AND menuItemId = :menuItemId
    """)
    suspend fun updateCount(userId: String, locationId: Int, menuItemId: Int, count: Int)

    @Query("""
        SELECT count FROM cart_items 
        WHERE userId = :userId 
        AND locationId = :locationId 
        AND menuItemId = :menuItemId 
        LIMIT 1
    """)
    suspend fun getCount(userId: String, locationId: Int, menuItemId: Int): Int?

    @Query("""
        SELECT * FROM cart_items 
        WHERE userId = :userId 
        AND locationId = :locationId
    """)
    suspend fun getItemsByLocation(userId: String, locationId: Int): List<CartItemEntity>

    @Query("""
        DELETE FROM cart_items 
        WHERE userId = :userId 
        AND locationId = :locationId
    """)
    suspend fun deleteItemsByLocation(userId: String, locationId: Int)

    @Query("""
        DELETE FROM cart_items 
        WHERE userId = :userId 
        AND count = 0
    """)
    suspend fun deleteZeroCountItems(userId: String)

    @Query("DELETE FROM cart_items WHERE userId = :userId")
    suspend fun clearCart(userId: String)

    @Delete
    suspend fun deleteItem(cartItem: CartItemEntity)
}