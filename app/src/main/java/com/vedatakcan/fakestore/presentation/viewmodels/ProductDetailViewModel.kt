package com.vedatakcan.fakestore.presentation.viewmodels

import android.os.Message
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.vedatakcan.fakestore.domain.models.Product
import com.vedatakcan.fakestore.domain.repository.ProductRepository
import com.vedatakcan.fakestore.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import androidx.lifecycle.viewModelScope
import com.vedatakcan.fakestore.domain.repository.CartRepository
import com.vedatakcan.fakestore.util.toCartItemEntity
import kotlinx.coroutines.flow.MutableSharedFlow

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _product = MutableStateFlow<Resource<Product>>(Resource.Loading())
    val product : StateFlow<Resource<Product>> = _product.asStateFlow()

    // Snackbar mesajları için bir flow tanımlıyoruz
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("productId")?.let { productId ->
            fetchProduct(productId)
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
    }

    private fun fetchProduct(productId: Int){
        viewModelScope.launch {
            productRepository.getProductById(productId).collect {
                _product.value = it
            }
        }
    }

    fun addToCart(product: Product){
        viewModelScope.launch {
            try {
                cartRepository.insert(product.toCartItemEntity())
                _eventFlow.emit(UiEvent.ShowSnackbar("Ürün Sepete Eklendi!"))
            }catch (e: Exception){
                _eventFlow.emit(UiEvent.ShowSnackbar("Sepete eklenirken hata oluştu."))
            }
        }
    }

}


