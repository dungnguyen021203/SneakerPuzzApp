package com.example.sneakerpuzzshop.presentation.ui.category

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.presentation.components.ProductCard
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.viewmodel.HomeViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.ProductViewModel
import com.example.sneakerpuzzshop.utils.ui.LoadingCircle

@Composable
fun CategoryProductsPage(
    modifier: Modifier = Modifier,
    categoryId: String,
    navController: NavHostController,
    viewModel: ProductViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(categoryId) {
        viewModel.loadProductsByCategory(categoryId)
    }

    val context = LocalContext.current
    val state by viewModel.product.collectAsState()
    val homeState by homeViewModel.categories.collectAsState()

    state.let {
        when(it) {
            is Resource.Failure -> {
                LaunchedEffect(state) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }
            is Resource.Loading -> {
                LoadingCircle()
            }
            is Resource.Success<*> -> {
                val productList = (state as Resource.Success<List<ProductModel>>).data

                LazyColumn(modifier = Modifier.fillMaxSize().padding(
                    WindowInsets.systemBars.asPaddingValues()
                ).padding(10.dp)) {
                    items(productList.chunked(2)) { rowItems ->
                        Row {
                            rowItems.forEach { product ->
                                val category = homeState.find { it.id == product.category}
                                ProductCard(product = product, categoryImage = category?.imageUrl, modifier = Modifier.weight(1f), navController = navController)
                            }
                            if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }

}