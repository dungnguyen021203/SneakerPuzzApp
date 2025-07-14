package com.example.sneakerpuzzshop.presentation.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.CartItemModel
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
    val userId = authViewModel.currentUser?.uid.toString()
    val context = LocalContext.current
    val state by viewModel.cart.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getCartFromUser(userId)
    }

    state.let {
        when (it) {
            is Resource.Failure -> {
                LaunchedEffect(state) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }

            is Resource.Loading -> LoadingCircle()
            is Resource.Success -> {
                val cartList = (state as Resource.Success).data
                Log.d("Cart", cartList.toString())
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn {
                        items(cartList) { item ->
                            CartItemRow(item = item, onAdd = {
                                viewModel.updateCart(
                                    userId,
                                    item.productId,
                                    item.size,
                                    item.quantity + 1
                                )
                            }, onRemove = {
                                viewModel.updateCart(
                                    userId,
                                    item.productId,
                                    item.size,
                                    item.quantity - 1
                                )
                            }, onDelete = {
                                viewModel.removeFromCart(userId, item.productId, item.size)
                            })
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItemModel,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onDelete: () -> Unit
) {
    Row(Modifier
        .fillMaxWidth()
        .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Text("SP: ${item.productId} - Size ${item.size}")
        Spacer(modifier = Modifier.weight(1f))
        Row {
            IconButton(onClick = onRemove) { Text("-") }
            Text("${item.quantity}")
            IconButton(onClick = onAdd) { Text("+") }
            IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, null) }
        }
    }
}