package com.vedatakcan.fakestore.domain.repository

import com.vedatakcan.fakestore.util.Resource
import com.vedatakcan.fakestore.data.database.entities.CartItemEntity
import com.vedatakcan.fakestore.domain.models.CartItem
import com.vedatakcan.fakestore.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getAllCartItems(): Flow<Resource<List<CartItem>>>
    suspend fun insert(product: Product)
    suspend fun delete(cartItem: CartItemEntity)

    suspend fun update(cartItem: CartItemEntity)
}