package com.example.sneakerpuzzshop.presentation.ui.product

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.presentation.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsBottomBar(
    userId: String,
    stock: Int,
    quantity: Int,
    productId: String,
    onQuantityChange: (Int) -> Unit,
    selectedSize: String?,
    cartViewModel: CartViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var isShowDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
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
                }
            }

            // Add to Bag Button
            Button(
                onClick = {
                    cartViewModel.addToCart(
                        userId, CartItemModel(
                            productId,
                            selectedSize ?: "", quantity
                        )
                    )
                    isShowDialog = true
                },
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

            if (isShowDialog == true) {
                AlertDialog(
                    onDismissRequest = { isShowDialog == false },
                    title = {Text(text = "Added to Cart")},
                    text = {
                        Text(text = "Product has been added to your cart successfully")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                isShowDialog = false
                                navController.popBackStack()
                            }
                        ) {
                            Text("Continue to shop")
                        }
                    }
                )
            }
        }
    }
}