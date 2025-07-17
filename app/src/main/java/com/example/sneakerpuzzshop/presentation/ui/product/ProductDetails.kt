package com.example.sneakerpuzzshop.presentation.ui.product

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.HomeViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.ProductViewModel
import com.example.sneakerpuzzshop.utils.ui.LoadingCircle

@Composable
fun ProductDetails(
    modifier: Modifier = Modifier,
    productId: String,
    navController: NavHostController,
    viewModel: ProductViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        viewModel.loadProductDetails(productId)
    }

    val context = LocalContext.current
    val state by viewModel.productDetails.collectAsState()
    val homeState by homeViewModel.categories.collectAsState()
    val userId = authViewModel.currentUser?.uid.toString()

    state.let {
        when (it) {
            is Resource.Failure -> {
                LaunchedEffect(state) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }

            is Resource.Loading -> LoadingCircle()
            is Resource.Success<*> -> {
                val product = (state as Resource.Success).data
                val category = homeState.find { it.id == product.category }

                ProductDetailsScreen(
                    userId = userId,
                    product = product,
                    categoryImage = category?.imageUrl,
                    navController = navController
                )
            }
        }
    }
}

