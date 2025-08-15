package com.vedatakcan.fakestore.data.repository

import com.vedatakcan.fakestore.data.database.dao.CartDao
import com.vedatakcan.fakestore.data.database.entities.CartItemEntity
import com.vedatakcan.fakestore.data.database.entities.toCartItem
import com.vedatakcan.fakestore.domain.models.CartItem
import com.vedatakcan.fakestore.domain.models.Product
import com.vedatakcan.fakestore.domain.repository.CartRepository
import com.vedatakcan.fakestore.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getAllCartItems(): Flow<Resource<List<CartItem>>> = flow {
        emit(Resource.Loading())
        try {
            cartDao.getAllCartItems().map { entities ->
                entities.map { it.toCartItem() }
            }.collect { cartItems ->
                emit(Resource.Success(cartItems))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Sepet verileri yüklenirken bir hata oluştu."))
        }
    }



    override suspend fun insert(product: Product) {
        val existingCartItem = cartDao.getCartItemByProductId(product.id)
        if (existingCartItem == null) {
            cartDao.insert(
                CartItemEntity(
                    productId = product.id,
                    title = product.title,
                    price = product.price,
                    description = product.description,
                    category = product.category,
                    imageUrl = product.imageUrl,
                    ratingRate = product.rating.rate,
                    ratingCount = product.rating.count,
                    quantity = 1
                )
            )
        }else {
            val updateItem = existingCartItem.copy(quantity = existingCartItem.quantity + 1)
            cartDao.update(updateItem)
        }
    }

    override suspend fun delete(cartItem: CartItemEntity) {
        cartDao.delete(cartItem)
    }

    override suspend fun update(cartItem: CartItemEntity) {
        cartDao.update(cartItem)
    }
}