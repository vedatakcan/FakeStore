package com.vedatakcan.fakestore.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vedatakcan.fakestore.data.database.dao.CartDao
import com.vedatakcan.fakestore.data.database.entities.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1, exportSchema = false)
abstract class FakeStoreDatabase: RoomDatabase() {
    // Veri tabanı için Dao'yu döndüren abstract fonksiyonu
    abstract fun cartDao(): CartDao
}