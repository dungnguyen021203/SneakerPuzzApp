package com.example.sneakerpuzzshop.presentation.ui.search

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.presentation.viewmodel.ProductViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.ProductCard
import com.example.sneakerpuzzshop.presentation.viewmodel.HomeViewModel

@Composable
fun SearchResults(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    initialQuery: String,
    productViewModel: ProductViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val everyProductState by productViewModel.everyProduct.collectAsState()

    LaunchedEffect(everyProductState) {
            productViewModel.onQueryChange(initialQuery)
            productViewModel.performSearch(initialQuery)

    }


    val result by productViewModel.searchResult.collectAsState()

    val homeState by homeViewModel.categories.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (result.isEmpty()) {
            item {
                Text("Không tìm thấy sản phẩm nào cho \"$initialQuery\"")
            }
        } else {
            items(result.chunked(2)) { row ->
                Row {
                    row.forEach { product ->
                        val category = homeState.find { it.id == product.category }
                        ProductCard(
                            product = product,
                            modifier = Modifier.weight(1f),
                            navController = navController,
                            categoryImage = category?.imageUrl
                        )
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(12.dp))

            }
        }
    }
}