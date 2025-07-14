package com.example.sneakerpuzzshop.presentation.ui.pages

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.CartViewModel
import com.example.sneakerpuzzshop.utils.LoadingCircle

@Composable
fun CartPage(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val userId = authViewModel.currentUser?.uid
    val context = LocalContext.current
    val state by viewModel.cart.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getCartFromUser(userId.toString())
    }

    state.let {
        when(it) {
            is Resource.Failure -> {
                LaunchedEffect(state) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }
            is Resource.Loading -> LoadingCircle()
            is Resource.Success -> {
                val cartList = (state as Resource.Success).data
                cartList.forEach { cart ->
                    Text(text = cart.productId)
                }
            }
        }
    }
}