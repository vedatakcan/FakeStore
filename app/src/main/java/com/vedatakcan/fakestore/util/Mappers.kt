package com.vedatakcan.fakestore.util

import com.vedatakcan.fakestore.data.database.entities.CartItemEntity
import com.vedatakcan.fakestore.domain.models.CartItem
import com.vedatakcan.fakestore.domain.models.Product

fun CartItem.toCartItemEntity(): CartItemEntity {
    return CartItemEntity(
        localId = this.id,
        productId = this.product.id,
        title = this.product.title,
        price = this.product.price,
        imageUrl = this.product.imageUrl,
        description = this.product.description,
        category = this.product.category,
        ratingCount = this.product.rating.count,
        ratingRate = this.product.rating.rate,
        quantity = this.quantity
    )
}