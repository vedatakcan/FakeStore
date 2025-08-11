package com.vedatakcan.fakestore.data.api

import com.vedatakcan.fakestore.data.models.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApiService {
    // Tüm ürünlerin getiren endpoint
    @GET("products")
    suspend fun getAllProducts(): Response<List<ProductDto>>

    //Id ye göre ürün getiren endpoint
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<ProductDto>

    // Tüm kategorileri getiren endpoint
    @GET("products/categories")
    suspend fun getAllCategories(): Response<List<String>>

    // Belirli bir kategoriye ait ürünleri getiren endpoint
    @GET("products/category{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): Response<List<ProductDto>>

}