package com.example.sneakerpuzzshop.presentation.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.presentation.components.home.BannerView
import com.example.sneakerpuzzshop.presentation.components.home.CategoryView
import com.example.sneakerpuzzshop.presentation.components.home.HeaderView
import com.example.sneakerpuzzshop.presentation.viewmodel.ProductViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.ProductModel
import com.example.sneakerpuzzshop.presentation.components.ProductCard
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.ui.search.SearchBarWithSuggestions
import com.example.sneakerpuzzshop.presentation.viewmodel.HomeViewModel
import com.example.sneakerpuzzshop.utils.ui.LoadingCircle
import com.example.sneakerpuzzshop.utils.ui.ROUTE_CATEGORY_PRODUCTS

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    productViewModel: ProductViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val productState by productViewModel.everyProduct.collectAsState()
    val homeState by homeViewModel.categories.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.getEveryProduct()
    }

    productState.let {
        when (it) {
            is Resource.Failure -> {
                LaunchedEffect(productState) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }

            is Resource.Loading -> {
                LoadingCircle()
            }

            is Resource.Success<*> -> {
                val productList = (productState as Resource.Success<List<ProductModel>>).data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            WindowInsets.systemBars.asPaddingValues()
                        )
                        .padding(10.dp),
                    contentPadding = PaddingValues(
                        bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 30.dp
                    )
                ) {
                    item {
                        HeaderView(modifier)
                        Spacer(modifier = Modifier.height(10.dp))

                        SearchBarWithSuggestions(navController = navController, autoFocus = false )
                        Spacer(modifier = Modifier.height(20.dp))
                        CategoryView(modifier, navController = navController)
                        BannerView(modifier = Modifier.height(200.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Top Sellers",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "See all",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.clickable {
                                    navController.navigate(ROUTE_CATEGORY_PRODUCTS + "nike")
                                })
                        }
                    }
                    items(productList.chunked(2)) { rowItems ->

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
    }
}