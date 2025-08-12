package com.vedatakcan.fakestore.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.vedatakcan.fakestore.presentation.viewmodels.ProductDetailViewModel
import com.vedatakcan.fakestore.util.Resource

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel()
){
    val productResource by viewModel.product.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (productResource) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Resource.Success -> {
                val product = productResource.data
                if (product != null) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            model =  product.imageUrl,
                            contentDescription = product.title,
                        )

                        Spacer(modifier = Modifier.padding(12.dp))

                        Text(text = product.title, style = MaterialTheme.typography.titleLarge)
                        Text(text = "${product.price}", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = product.description, style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            is Resource.Error -> {
                Text(
                    text = productResource.message ?: "Ürün detayları yüklenirken hata oluştu.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}

