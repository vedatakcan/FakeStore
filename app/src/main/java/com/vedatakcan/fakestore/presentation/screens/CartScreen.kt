package com.vedatakcan.fakestore.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.vedatakcan.fakestore.domain.models.CartItem
import com.vedatakcan.fakestore.presentation.components.CartItemCard
import com.vedatakcan.fakestore.presentation.viewmodels.CartViewModel
import com.vedatakcan.fakestore.util.Resource

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
){
    val cartItemResource by viewModel.cartItems.collectAsState()

    Box(modifier = Modifier.fillMaxSize()){
        when(cartItemResource){
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Resource.Success -> {
                val cartItems = cartItemResource.data ?: emptyList()
                if (cartItems.isEmpty()) {
                    Text("Sepetinizde ürün bulunmamaktadır",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(cartItems) { cartItem ->
                            CartItemCard(
                                cartItem = cartItem,
                                onRemoveClick = {viewModel.removeCartItem(cartItem)},
                                onItemClick = {
                                    navController.navigate("product_detail_screen/${cartItem.product.id}")
                                }

                            )
                        }
                    }
                }
            }
            is Resource.Error -> {
                Text(
                    text = cartItemResource.message ?: "Beklenemedik bir hata oluştu",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

