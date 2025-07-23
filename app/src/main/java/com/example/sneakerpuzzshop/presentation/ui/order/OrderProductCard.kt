package com.example.sneakerpuzzshop.presentation.ui.order

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.sneakerpuzzshop.domain.model.CartItemModel
import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.utils.others.formatCurrency
import com.example.sneakerpuzzshop.utils.ui.ROUTE_ORDER_DETAILS
import com.example.sneakerpuzzshop.utils.ui.ROUTE_PRODUCTS_DETAILS

@Composable
fun OrderProductCard(
    modifier: Modifier = Modifier,
    product: ProductModel?,
    orderItem: CartItemModel,
    orderId: String,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = product?.images?.firstOrNull(),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = product?.category.toString().replaceFirstChar { it.toUpperCase() }, fontSize = 12.sp)
                    Icon(
                        Icons.Default.Verified,
                        contentDescription = "Icon Verified",
                        tint = Color.Blue,
                        modifier = Modifier.size(12.dp)
                    )
                }
                Text(
                    text = "${product?.name}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = formatCurrency(product?.actualPrice),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("Size: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append(orderItem.size)
                        }
                        append(" --- ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("Quantity: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append("${orderItem.quantity}")
                        }
                    },
                    color = Color.Black,
                    fontSize = 12.sp
                )

            }

            Text(text = "View", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.clickable {
                navController.navigate(ROUTE_PRODUCTS_DETAILS + product?.id) {
                    popUpTo(ROUTE_ORDER_DETAILS + orderId) {inclusive = false}
                }
            })
        }
    }
}