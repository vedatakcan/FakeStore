package com.vedatakcan.fakestore.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vedatakcan.fakestore.domain.models.CartItem
import com.vedatakcan.fakestore.domain.models.Product
import com.vedatakcan.fakestore.domain.models.Rating

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,
    val productId: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val imageUrl: String,
    val ratingRate: Double,
    val ratingCount: Int,
    val quantity: Int
)

fun CartItemEntity.toCartItem(): CartItem {
    return CartItem(
        id = this.localId,
        product = Product(
            id = this.productId,
            title = this.title,
            price = this.price,
            description = this.description,
            category = this.category,
            imageUrl = this.imageUrl,
            rating = Rating(rate = this.ratingRate, count = this.ratingCount)
        ),
        quantity = this.quantity
    )
}
