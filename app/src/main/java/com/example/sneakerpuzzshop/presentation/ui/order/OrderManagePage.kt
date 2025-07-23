package com.example.sneakerpuzzshop.presentation.ui.order

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.domain.model.OrderModel
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.OrderViewModel
import com.example.sneakerpuzzshop.utils.others.ORDER_STATUS_LIST
import com.example.sneakerpuzzshop.utils.ui.LoadingCircle
import com.example.sneakerpuzzshop.utils.ui.ROUTE_ORDER

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderManagePage(
    modifier: Modifier = Modifier,
    orderStatus: String,
    navController: NavHostController,
    orderViewModel: OrderViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val userId = authViewModel.currentUser?.uid
    val orderState by orderViewModel.order.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        orderViewModel.getOrder(userId.toString(), orderStatus)
    }

    var isChoosing by remember { mutableStateOf(orderStatus) }

    orderState.let {
        when (it) {
            is Resource.Failure -> {
                LaunchedEffect(orderState) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }

            is Resource.Loading -> LoadingCircle()
            is Resource.Success<*> -> {
                val orderList = (orderState as Resource.Success<List<OrderModel>>).data
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "My Orders", fontWeight = FontWeight.Bold)
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(ORDER_STATUS_LIST) { item ->
                                Card(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .height(40.dp)
                                        .clip(RoundedCornerShape(30.dp))
                                        .clickable {
                                            isChoosing = item
                                            navController.navigate(ROUTE_ORDER + item) {
                                                popUpTo(ROUTE_ORDER + orderStatus) {
                                                    inclusive = true
                                                }
                                            }

                                        },
                                    elevation = CardDefaults.cardElevation(1.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isChoosing == item) Color.Red else Color(
                                            0xFFF0F0F0
                                        ),
                                        contentColor = if (isChoosing == item) Color.White else Color.Black
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 20.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = item.lowercase()
                                                .replaceFirstChar { it.uppercase() },
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                        if(orderList.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 100.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Receipt,
                                    contentDescription = "Empty Cart",
                                    tint = Color.LightGray,
                                    modifier = Modifier.size(100.dp),
                                )
                                Text(
                                    text = "It is empty here",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 16.dp)
                                )
                                Text(
                                    text = "Looks like you haven’t anything here",
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
                                    lineHeight = 20.sp
                                )
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(orderList) { order ->
                                    OrderCard(order = order, navController = navController)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}