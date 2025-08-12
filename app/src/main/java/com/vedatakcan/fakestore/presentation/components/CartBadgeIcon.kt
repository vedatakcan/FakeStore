package com.vedatakcan.fakestore.presentation.components

import android.R.attr.x
import android.text.Layout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CartBadgeIcon(
    count: Int,
    onCartClick: () -> Unit,
){
    Box(modifier = Modifier.padding(end = 8.dp)) {
        IconButton(onClick = onCartClick) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Sepet",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        if (count > 0) {
            Badge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-4).dp, y = 4.dp)
            ) {
                Text(
                    text = count.toString(),
                    fontSize = 10.sp
                )
            }
        }
    }
}
