package com.example.sneakerpuzzshop.presentation.ui.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.presentation.viewmodel.ProductViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.utils.LoadingCircle

@Composable
fun ProductDetails(
    modifier: Modifier = Modifier,
    productId: String,
    navController: NavHostController,
    viewModel: ProductViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        viewModel.loadProductDetails(productId)
    }

    val context = LocalContext.current
    val state by viewModel.productDetails.collectAsState()

    state.let {
        when(it) {
            is Resource.Failure -> {
                LaunchedEffect(state) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }
            is Resource.Loading -> LoadingCircle()
            is Resource.Success<*> -> {
                val product = (state as Resource.Success).data
                Text(text = product.name)
            }
        }
    }
}