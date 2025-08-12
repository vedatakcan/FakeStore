package com.vedatakcan.fakestore.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.vedatakcan.fakestore.data.api.FakeStoreApiService
import com.vedatakcan.fakestore.data.database.FakeStoreDatabase
import com.vedatakcan.fakestore.data.database.dao.CartDao
import com.vedatakcan.fakestore.data.repository.CartRepositoryImpl
import com.vedatakcan.fakestore.data.repository.ProductRepositoryImpl
import com.vedatakcan.fakestore.domain.repository.CartRepository
import com.vedatakcan.fakestore.domain.repository.ProductRepository
import com.vedatakcan.fakestore.util.Contants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FakeStoreDatabase {
        return Room.databaseBuilder(
            context,
            FakeStoreDatabase::class.java,
            "fake_store_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideFakeStoreApi(): FakeStoreApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FakeStoreApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: FakeStoreApiService): ProductRepository {
        return ProductRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun ProvideCartDao(database: FakeStoreDatabase): CartDao {
        return database.cartDao()
    }


    @Provides
    @Singleton
    fun ProvideCartRepository(dao: CartDao): CartRepository {
        return CartRepositoryImpl(dao)
    }
}