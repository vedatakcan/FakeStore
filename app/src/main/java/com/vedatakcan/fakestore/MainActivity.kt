package com.vedatakcan.fakestore

import android.R.attr.title
import android.accounts.Account
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vedatakcan.fakestore.presentation.components.CartBadgeIcon
import com.vedatakcan.fakestore.presentation.screens.HomeScreen
import com.vedatakcan.fakestore.presentation.screens.ProductDetailScreen
import com.vedatakcan.fakestore.presentation.ui.theme.FakeStoreTheme
import com.vedatakcan.fakestore.presentation.viewmodels.CartViewModel
import com.vedatakcan.fakestore.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FakeStoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    AppNavigation()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val cartViewModel: CartViewModel = hiltViewModel()
    val cartItemCount by cartViewModel.cartItemCount.collectAsState()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val searchText by homeViewModel.searchText.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (currentRoute == "home_screen") {
                        Row(
                            modifier = Modifier.fillMaxWidth(), // fillMaxSize() yerine fillMaxWidth() kullanıldı
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = searchText,
                                onValueChange = { homeViewModel.onSearchTextChange(it) },
                                modifier = Modifier.weight(1f),
                                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Arama" ) },
                                placeholder = { Text("Ürün ara..") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent
                                )
                            )
                        }
                    } else if (currentRoute?.startsWith("product_detail_screen") == true) {
                        Text("ÜRÜN DETAY")
                    } else {
                        Text("Fake Store")
                    }
                },
                actions = {
                    CartBadgeIcon(
                        count = cartItemCount,
                        onCartClick = { navController.navigate("cart_screen") }
                    )
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home_screen",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home_screen") {
                HomeScreen(navController = navController, viewModel = homeViewModel )
            }
            composable(
                route = "product_detail_screen/{productId}",
                arguments = listOf(
                    navArgument("productId") {
                        type = NavType.IntType
                    }
                )) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") // 'productId' key'i düzeltildi
                if (productId != null) {
                    ProductDetailScreen(productId = productId, navController = navController) // Parametreler eklendi
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FakeStoreTheme {
        AppNavigation()
    }
}