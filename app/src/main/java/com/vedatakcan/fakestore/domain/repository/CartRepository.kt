package com.vedatakcan.fakestore.domain.repository

import com.vedatakcan.fakestore.util.Resource
import com.vedatakcan.fakestore.data.database.entities.CartItemEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getAllCartItems(): Flow<Resource<List<CartItemEntity>>>
    suspend fun insert(cartItem: CartItemEntity)
    suspend fun delete(cartItem: CartItemEntity)
}