package com.example.sneakerpuzzshop.presentation.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.utils.formatCurrency

@Composable
fun ProductDetailsContent(
    modifier: Modifier = Modifier,
    product: ProductModel,
    categoryImage: String?
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Rating
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "${product.rating}",
                        color = Color.Black,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Icon(Icons.Default.Share, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Name
            Text(
                text = product.name,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Price and Label
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFFEB3B))
                        .padding(5.dp)
                ) {
                    Text(
                        text = "-50%",
                        style = TextStyle(fontSize = 12.sp),
                        color = Color.Black,
                    )
                }

                Text(
                    text = formatCurrency(product.actualPrice),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = formatCurrency(product.actualPrice),
                    color = Color.Red,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.LineThrough
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            // Stock Status
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append("Stock: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(product.stockStatus)
                    }
                },
                color = Color.Black,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Category
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = categoryImage,
                    contentDescription = "Category"
                )
                Text(
                    text = product.category.replaceFirstChar { it.uppercase() },
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    Icons.Default.Verified,
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.size(15.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Size Choosing
            Text(
                text = "Size",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                mapOf<String, Int>(
                    "EU 38" to 10,
                    "EU 39" to 11,
                    "EU 40" to 1,
                    "EU 41" to 0,
                    "EU 42" to 9,
                    "EU 43" to 10,
                    "EU 44" to 11,
                ).keys.chunked(3).forEach { rowSize ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowSize.forEach { size ->
                            OutlinedButton(
                                onClick = { /* TODO */ },
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = size)
                            }
                        }
                        repeat(3 - rowSize.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Description
            Text(text = "Product description: ", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.description, fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            HorizontalDivider(color = Color.Black)

            Spacer(modifier = Modifier.height(15.dp))

            // Reviews
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Reviews: (199)", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
            }
        }
    }

}
