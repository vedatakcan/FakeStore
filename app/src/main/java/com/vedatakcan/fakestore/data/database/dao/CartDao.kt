package com.vedatakcan.fakestore.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vedatakcan.fakestore.data.database.entities.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    // Sepetlerdeki ürünleri getiren sorgu
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItemEntity>>

    // Sepete yerin ürün ekleyen veya var olanı günneleyen metod
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItemEntity)

    // Sepetten ürün silen metod
    @Delete
    suspend fun delete(cartItem: CartItemEntity)

    // Sepetteki bir ürünü güncelleyen metod
    @Update
    suspend fun update(cartItem: CartItemEntity)

    // Sepeti Tamamen Temizleyen metod
    @Query("DELETE FROM cart_items")
    suspend fun deleteAll()



}