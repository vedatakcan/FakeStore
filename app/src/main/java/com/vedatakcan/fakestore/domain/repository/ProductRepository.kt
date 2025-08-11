package com.vedatakcan.fakestore.domain.repository

import com.vedatakcan.fakestore.util.Resource

import com.vedatakcan.fakestore.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(): Flow<Resource<List<Product>>>
    fun getProductById(id: Int): Flow<Resource<Product>>
    fun getCategories(): Flow<Resource<List<String>>>

}