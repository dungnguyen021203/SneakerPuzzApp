package com.example.sneakerpuzzshop.presentation.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.ui.cart.CartItemRow
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.CartViewModel
import com.example.sneakerpuzzshop.utils.ui.LoadingCircleWholePage
import com.example.sneakerpuzzshop.utils.ui.ROUTE_CHECKOUT

@Composable
fun CartPage(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val userId = authViewModel.currentUser?.uid.toString()
    val context = LocalContext.current
    val state by viewModel.cart.collectAsState()
    val productMap by viewModel.productDetailsMap.collectAsState()

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

            is Resource.Loading -> LoadingCircleWholePage()

            is Resource.Success -> {
                val cartList = (state as Resource.Success).data
                val isEmpty = cartList.isEmpty()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.systemBars.asPaddingValues())
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 30.dp
                    )
                ) {
                    if (isEmpty) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                    WindowInsets.systemBars.asPaddingValues()
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingBag,
                                    contentDescription = "Empty Cart",
                                    tint = Color.LightGray,
                                    modifier = Modifier.size(100.dp),
                                )
                                Text(
                                    text = "Your Cart is Empty",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 16.dp)
                                )
                                Text(
                                    text = "Looks like you haven’t added anything to your cart yet.",
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    } else {
                        items(cartList) { item ->
                            val product = productMap[item.productId]

                            if(product != null) {
                                CartItemRow(
                                    item = item,
                                    onAdd = {
                                        viewModel.updateCart(
                                            userId,
                                            item.productId,
                                            item.size,
                                            item.quantity + 1
                                        )
                                    },
                                    onRemove = {
                                        viewModel.updateCart(
                                            userId,
                                            item.productId,
                                            item.size,
                                            item.quantity - 1
                                        )
                                    },
                                    onDelete = {
                                        viewModel.removeFromCart(userId, item.productId, item.size)
                                    },
                                    product = product
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                    }

                    item {
                        Button(
                            onClick = { navController.navigate(ROUTE_CHECKOUT) },
                            enabled = !isEmpty,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1A50FF),
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "Check Out", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}
