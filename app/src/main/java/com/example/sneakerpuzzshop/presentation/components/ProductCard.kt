package com.example.sneakerpuzzshop.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.presentation.viewmodel.FavoriteViewModel
import com.example.sneakerpuzzshop.utils.ui.ROUTE_PRODUCTS_DETAILS
import com.example.sneakerpuzzshop.utils.others.formatCurrency
import androidx.compose.runtime.getValue

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: ProductModel,
    navController: NavHostController,
    categoryImage: String?,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val favorites by favoriteViewModel.favorites.collectAsState()
    val isFavorite = product.id in favorites

    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable {
                navController.navigate(ROUTE_PRODUCTS_DETAILS + product.id)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF5F5F5))
            ) {
                AsyncImage(
                    model = product.images.firstOrNull(),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )

                // -50% label
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .background(Color(0xFFFFEB3B), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = "-50%",
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )
                }

                // Heart icon
                IconButton(
                    onClick = {
                        favoriteViewModel.toggleFavorite(product.id)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(20.dp),
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                style = TextStyle(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.category.replaceFirstChar { it.uppercase() },
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = formatCurrency(product.price),
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Gray
                    )
                    Text(
                        text = formatCurrency(product.actualPrice),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = categoryImage,
                        contentDescription = "Category Image",
                        modifier = Modifier.background(Color(0xFFF5F5F5))
                    )
                }
            }
        }
    }
}

