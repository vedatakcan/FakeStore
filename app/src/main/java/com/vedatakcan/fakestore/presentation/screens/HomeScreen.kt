package com.vedatakcan.fakestore.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vedatakcan.fakestore.presentation.components.ProductCard
import com.vedatakcan.fakestore.presentation.viewmodels.HomeViewModel
import com.vedatakcan.fakestore.util.Resource

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),

) {
    val productResource by viewModel.filteredProducts.collectAsState()

    Box(modifier = Modifier.fillMaxSize()){
        when(productResource) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Resource.Success -> {
                val products = productResource.data ?: emptyList()
                if (products.isEmpty()){
                    Text(text = "Ürün bulunamadı.")
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()){
                        items(products) { product ->
                            ProductCard(
                                product = product,
                                onItemClick = { productId ->
                                    navController.navigate("product_detail_screen/$productId")
                                }
                            )
                        }
                    }
                }
            }
            is Resource.Error -> {
                Text(
                    text = productResource.message ?: "Beklenmedik bir hataoluştu.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}