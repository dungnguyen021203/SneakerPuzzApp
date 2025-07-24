package com.example.sneakerpuzzshop.presentation.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.presentation.components.ProductCard
import com.example.sneakerpuzzshop.presentation.viewmodel.FavoriteViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.HomeViewModel

@Composable
fun FavoritePage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val favoriteProducts by favoriteViewModel.favoriteProducts.collectAsState()
    val homeState by homeViewModel.categories.collectAsState()


    if (favoriteProducts.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Chưa có sản phẩm yêu thích", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
                .padding(10.dp)
        ) {
            items(favoriteProducts.chunked(2)) { rowItems ->
                Row {
                    rowItems.forEach { product ->
                        val category = homeState.find { it.id == product.category }
                        ProductCard(
                            product = product,
                            categoryImage = category?.imageUrl,
                            modifier = Modifier.weight(1f),
                            navController = navController
                        )
                    }
                    if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}