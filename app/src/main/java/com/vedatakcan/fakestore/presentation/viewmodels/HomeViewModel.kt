package com.vedatakcan.fakestore.presentation.viewmodels

import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vedatakcan.fakestore.data.repository.ProductRepositoryImpl
import com.vedatakcan.fakestore.domain.models.Product
import com.vedatakcan.fakestore.domain.repository.ProductRepository
import com.vedatakcan.fakestore.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository : ProductRepository
): ViewModel() {

    private val _products = MutableStateFlow<Resource<List<Product>>> (Resource.Loading())
    val products: StateFlow<Resource<List<Product>>> = _products.asStateFlow()

    private val _categories = MutableStateFlow<Resource<List<String>>>(Resource.Loading())
    val categories: StateFlow<Resource<List<String>>> = _categories.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _filteredProducts = MutableStateFlow<Resource<List<Product>>> (Resource.Loading())
    val filteredProducts: StateFlow<Resource<List<Product>>> = _filteredProducts.asStateFlow()


    init {
        fetchData()
        observeData()
    }


    private fun fetchData() {
        viewModelScope.launch {
            productRepository.getAllProducts().collect { _products.value = it }
            productRepository.getCategories().collect { _categories.value = it }
        }
    }


    private fun observeData() {
        viewModelScope.launch {
            combine(_products, _searchText, _selectedCategory) {productsResource,  searchText, selectedCategory ->
                when(productsResource) {
                    is Resource.Success -> {
                        val filteredList = productsResource.data?.filter {product ->
                            (searchText.isBlank() || product.title.contains(searchText, true)) &&
                                    (selectedCategory == null || product.category == selectedCategory)
                        }
                        Resource.Success(filteredList ?: emptyList())
                    }
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Error -> Resource.Error(productsResource.message ?: "Bir hata oluştu.")
                }
            }.collect { _filteredProducts.value = it }
        }
    }

    fun onSearchTextChange(text: String){
        _searchText.value = text
    }

    fun onCategorySelected(category: String){
        _selectedCategory.value = if (category == _selectedCategory.value) null else category
    }


}