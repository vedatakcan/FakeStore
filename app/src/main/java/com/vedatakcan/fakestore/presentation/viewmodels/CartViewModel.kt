package com.vedatakcan.fakestore.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vedatakcan.fakestore.data.database.entities.CartItemEntity
import com.vedatakcan.fakestore.domain.models.CartItem
import com.vedatakcan.fakestore.domain.repository.CartRepository
import com.vedatakcan.fakestore.util.Resource
import com.vedatakcan.fakestore.util.toCartItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {


    // Sepette ürünlerin sayısını tutan Flow
    val cartItemCount: StateFlow<Int> = cartRepository.getAllCartItems()
        .map { resource ->
            resource.data?.size ?: 0
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    // Sepette ürünlerin listesini tutan flow
    val cartItems: StateFlow<Resource<List<CartItem>>> = cartRepository.getAllCartItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Loading()
        )

    // Sepetten ürün silme fonksiyonu
    fun removeCartItem(cartItem: CartItem){
        viewModelScope.launch {
            cartRepository.delete(
                CartItemEntity(
                    localId = cartItem.id,
                    productId = cartItem.product.id,
                    title = cartItem.product.title,
                    price = cartItem.product.price,
                    description = cartItem.product.description,
                    category = cartItem.product.category,
                    imageUrl = cartItem.product.imageUrl,
                    ratingRate = cartItem.product.rating.rate,
                    ratingCount = cartItem.product.rating.count,
                    quantity = cartItem.quantity
                )
            )
        }
    }

    fun increaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            val updateItem = cartItem.copy(quantity = cartItem.quantity +1)
            cartRepository.update(updateItem.toCartItemEntity())
        }
    }

    fun decreaseQuantity (cartItem: CartItem) {
        viewModelScope.launch {
            if (cartItem.quantity > 1) {
                val updatedItem = cartItem.copy(quantity = cartItem.quantity -1)
                cartRepository.update(updatedItem.toCartItemEntity())
            } else {
                // Miktar bir ise ürünü sepetten tamamen sil
                cartRepository.delete(cartItem.toCartItemEntity())
            }
        }
    }
}