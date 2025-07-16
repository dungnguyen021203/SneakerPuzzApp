package com.example.sneakerpuzzshop.presentation.ui.product

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductDetailsBottomBar(
    userId: String,
    stock: Int,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    selectedSize: String?
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth().navigationBarsPadding(),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Quantity
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = {
                        if (quantity > 1) onQuantityChange(quantity - 1)
                    },
                    enabled = selectedSize != null && quantity > 1
                ) {
                    Icon(Icons.Default.RemoveCircle, contentDescription = "Decrease")
                }

                Text(text = "$quantity", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

                IconButton(
                    onClick = {
                        if (quantity < stock) onQuantityChange(quantity + 1)
                    },
                    enabled = selectedSize != null && quantity < stock
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Increase")
                    Log.d("AddToCart", "User: $userId - Size: $selectedSize - Quantity: $quantity")
                }
            }

            // Add to Bag Button
            Button(
                onClick = { /* Add to Bag */ },
                enabled = selectedSize != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.ShoppingBag, contentDescription = "Add to Bag")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add to Bag")
            }
        }
    }
}