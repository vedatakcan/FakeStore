package com.vedatakcan.fakestore.data.repository

import com.vedatakcan.fakestore.data.api.FakeStoreApiService
import com.vedatakcan.fakestore.data.models.ProductDto
import com.vedatakcan.fakestore.domain.models.Product
import com.vedatakcan.fakestore.domain.models.Rating
import com.vedatakcan.fakestore.domain.repository.ProductRepository
import com.vedatakcan.fakestore.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: FakeStoreApiService
): ProductRepository {
    override fun getAllProducts(): Flow<Resource<List<Product>>> = flow{
        emit(Resource.Loading())
        try {
            val response = api.getAllProducts()
            if (response.isSuccessful){
                val products = response.body()?.map { it.toProduct() } ?: emptyList()
                emit(Resource.Success(products))
            }else{
                emit(Resource.Error("Api İsteği Başarısız oldu. ${response.code()}"))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Ağ hatası. ${e.message}"))
        }catch (e: IOException){
            emit(Resource.Error("İnternet bağlantısı yok. ${e.message}"))
        }
    }

    override fun getProductById(id: Int): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getProductById(id)
            if (response.isSuccessful){
                val product = response.body()?.toProduct()
                if (product != null){
                    emit(Resource.Success(product))
                }else{
                    emit(Resource.Error("Ürün bulunamadı."))
                }
            }else{
                emit(Resource.Error("Api isteği başarısız oldu. ${response.code()}"))
            }
            }catch (e: HttpException){
            emit(Resource.Error("Ağ hatası. ${e.message}"))
        }catch (e: IOException){
            emit(Resource.Error("İnternet bağlantısı yok. ${e.message}"))
        }
    }

    override fun getCategories(): Flow<Resource<List<String>>> = flow{
        emit(Resource.Loading())
        try {
            val response = api.getAllCategories()
            if (response.isSuccessful){
                val categories = response.body() ?: emptyList()
                emit(Resource.Success(categories))
            }else{
                emit(Resource.Error("Api isteği başarısız oldu. ${response.code()}"))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Ağ hatası. ${e.message}"))
        }catch (e: IOException){
            emit(Resource.Error("İnternet bağlantısı yok. ${e.message}"))
        }
    }

    private fun ProductDto.toProduct(): Product {
        return Product(
            id = this.id,
            title = this.title,
            price = this.price,
            description = this.description,
            category = this.category,
            imageUrl = this.image,
            rating = Rating(
                rate = this.rating.rate,
                count = this.rating.count
            )
        )
    }
}