package com.vedatakcan.fakestore.presentation.viewmodels

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

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _product = MutableStateFlow<Resource<Product>>(Resource.Loading())
    val product : StateFlow<Resource<Product>> = _product.asStateFlow()

    init {
        savedStateHandle.get<Int>("productId")?.let { productId ->
            fetchProduct(productId)
        }
    }

    private fun fetchProduct(productId: Int){
        viewModelScope.launch {
            productRepository.getProductById(productId).collect {
                _product.value = it
            }
        }
    }

}


