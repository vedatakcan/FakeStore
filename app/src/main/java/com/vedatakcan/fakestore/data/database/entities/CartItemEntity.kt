package com.vedatakcan.fakestore.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,
    val productId: Int,
    val title: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int
)
