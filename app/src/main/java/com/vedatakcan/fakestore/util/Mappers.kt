package com.vedatakcan.fakestore.util

import com.vedatakcan.fakestore.data.database.entities.CartItemEntity
import com.vedatakcan.fakestore.domain.models.Product

fun Product.toCartItemEntity(): CartItemEntity {
    return CartItemEntity(
        productId = this.id,
        title = this.title,
        price = this.price,
        imageUrl = this.imageUrl,
        description = this.description,
        category = this.category,
        ratingCount = this.rating.count,
        ratingRate = this.rating.rate,
        quantity = 1
    )
}