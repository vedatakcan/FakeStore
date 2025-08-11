package com.vedatakcan.fakestore.data.repository

import androidx.compose.material3.darkColorScheme
import com.vedatakcan.fakestore.data.database.dao.CartDao
import com.vedatakcan.fakestore.data.database.entities.CartItemEntity
import com.vedatakcan.fakestore.domain.repository.CartRepository
import com.vedatakcan.fakestore.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
): CartRepository {
    override fun getAllCartItems(): Flow<Resource<List<CartItemEntity>>> = flow {
        emit(Resource.Loading())
        try {
            cartDao.getAllCartItems().collect { cartItems ->
                emit(Resource.Success(cartItems))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Sepet verileri yüklenirken bir hata oluştu."))
        }
    }

    override suspend fun insert(cartItem: CartItemEntity) {
       cartDao.insert(cartItem)
    }

    override suspend fun delete(cartItem: CartItemEntity) {
        cartDao.delete(cartItem)
    }
}