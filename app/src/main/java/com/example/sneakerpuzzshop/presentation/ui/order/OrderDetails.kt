package com.example.sneakerpuzzshop.presentation.ui.order

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.CartViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.OrderViewModel
import com.example.sneakerpuzzshop.utils.others.ORDER_STATUS_LIST
import com.example.sneakerpuzzshop.utils.ui.LoadingCircle
import com.example.sneakerpuzzshop.utils.ui.ROUTE_LOGIN
import com.example.sneakerpuzzshop.utils.ui.ROUTE_ORDER
import com.example.sneakerpuzzshop.utils.ui.ROUTE_ORDER_DETAILS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetails(
    modifier: Modifier = Modifier,
    orderId: String,
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var showDeleteDialog by remember { mutableStateOf(false) }

    val userId = authViewModel.currentUser?.uid!!.toString()

    val productMap by cartViewModel.productDetailsMap.collectAsState()
    val orderState by orderViewModel.orderDetails.collectAsState()
    val cancelOrderState by orderViewModel.cancelOrder.collectAsState()

    LaunchedEffect(orderId) {
        cartViewModel.getCartFromUser(userId)
        orderViewModel.getOrderDetails(orderId)
    }

    LaunchedEffect(cancelOrderState) {
        cancelOrderState.let {
            when (it) {
                is Resource.Success -> {
                    showToast(context, "Cancel order thành công!")
                    navController.navigate(ROUTE_ORDER + ORDER_STATUS_LIST[3]) {
                        popUpTo(ROUTE_ORDER_DETAILS + orderId) {
                            inclusive = true
                        }
                        launchSingleTop = true

                    }
                }

                is Resource.Failure -> {
                    showToast(context = context, message = it.exception.message.toString())
                }

                else -> {}

            }
        }
    }

    orderState.let {
        when (it) {
            is Resource.Failure -> {
                LaunchedEffect(orderState) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }

            is Resource.Loading -> {
                LoadingCircle()
            }

            is Resource.Success<*> -> {
                val order = (orderState as Resource.Success).data

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Order Review", fontWeight = FontWeight.Bold)
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentPadding = PaddingValues(bottom = 30.dp)
                    ) {
                        // Card
                        items(order.items) { orderItem ->
                            val product = productMap[orderItem.productId]
                            OrderProductCard(
                                product = product,
                                orderItem = orderItem,
                                navController = navController,
                                orderId = orderId
                            )
                        }

                        // Order Info
                        item {
                            OrderBottom(
                                userName = order.userName,
                                userPhoneNumber = order.userPhoneNumber,
                                userAddress = order.userAddress,
                                billingResult = order.billingResult,
                                navController = navController,
                                orderDate = order.date,
                                orderStatus = order.status
                            )
                        }

                        // Cancel Order
                        item {
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Cancel Order",
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .clickable {
                                            showDeleteDialog = true
                                        },
                                    color = Color.Red,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        item {
                            if (showDeleteDialog) {
                                AlertDialog(
                                    onDismissRequest = { showDeleteDialog = false },
                                    title = { Text("Xác nhận bỏ đơn hàng") },
                                    text = { Text("Bạn có chắc chắn muốn bỏ đơn hàng này? Hành động này không thể hoàn tác.") },
                                    confirmButton = {
                                        Button(onClick = {
                                            orderViewModel.cancelOrder(orderId)
                                            showDeleteDialog = false
                                        }) {
                                            Text("Xoá")
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = { showDeleteDialog = false }) {
                                            Text("Huỷ")
                                        }
                                    }
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}