package com.vedatakcan.fakestore.presentation.components

import android.R.attr.text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vedatakcan.fakestore.domain.models.Product
import com.vedatakcan.fakestore.domain.models.Rating
import org.jetbrains.annotations.Async

@Composable
fun ProductCard(
    product: Product,
    onItemClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(product.id)}
    ){
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model =  product.imageUrl,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "${product.price}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview(){
    val sampleProduct = Product(
        id = 1,
        title = "Sample Product",
        price = 19.99,
        description = "This is a sample product description.",
        category = "Electronics",
        imageUrl = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
        rating = Rating(rate = 3.9, count = 120)
    )
    ProductCard(product = sampleProduct, onItemClick = {})
}