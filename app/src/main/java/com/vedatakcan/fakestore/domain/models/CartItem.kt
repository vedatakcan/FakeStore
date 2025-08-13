package com.vedatakcan.fakestore.domain.models

data class CartItem(
    val id: Int,
    val product: Product,
    val quantity: Int = 1
)
