package com.example.sneakerpuzzshop.presentation.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.sneakerpuzzshop.domain.model.ProductModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    userId: String,
    product: ProductModel,
    navController: NavHostController,
    categoryImage: String?
) {

    var firstPic by remember {
        mutableStateOf(product.images.firstOrNull())
    }
    var selectedSize by remember { mutableStateOf<String?>(null) }
    var quantity by remember { mutableStateOf(1) }
    val stock = selectedSize?.let { product.sizes[it] ?: 0 } ?: 0

    Scaffold(
        bottomBar = {
            ProductDetailsBottomBar(
                userId = userId,
                stock = stock,
                quantity = quantity,
                productId = product.id,
                onQuantityChange = { quantity = it },
                selectedSize = selectedSize,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF5F5F5))
            ) {
                AsyncImage(
                    model = firstPic,
                    contentDescription = "Big picture in product details",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                product.images.forEach { pics ->
                    AsyncImage(
                        model = pics,
                        contentDescription = "Small Pictures in Product Details",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5F5F5))
                            .clickable{firstPic = pics}
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 10.dp)
            ) {
                item {
                    ProductDetailsContent(
                        product = product,
                        categoryImage = categoryImage,
                        navController = navController,
                        selectedSize = selectedSize,
                        onSizeSelected = { size ->
                            selectedSize = if (size.isNotEmpty()) size else null
                            quantity = 1
                        }
                    )
                }
            }
        }
    }
}