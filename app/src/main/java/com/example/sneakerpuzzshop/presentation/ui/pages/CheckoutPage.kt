package com.example.sneakerpuzzshop.presentation.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.presentation.viewmodel.CartViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.ui.checkout.CheckoutBottom
import com.example.sneakerpuzzshop.presentation.ui.checkout.CheckoutProductCard
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.utils.LoadingCircle

@Composable
fun CheckoutPage(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val userId = authViewModel.currentUser?.uid!!.toString()
    val user = authViewModel.currentUser
    val cartState by cartViewModel.cart.collectAsState()
    val productMap by cartViewModel.productDetailsMap.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(userId) {
        cartViewModel.getCartFromUser(userId)
    }

    cartState.let {
        when (it) {
            is Resource.Failure -> {
                LaunchedEffect(cartState) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }

            is Resource.Loading -> {
                LoadingCircle()
            }

            is Resource.Success<*> -> {
                val cartList = (cartState as Resource.Success).data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.systemBars.asPaddingValues()),
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    // Card
                    items(cartList) { cart ->
                        val product = productMap[cart.productId]
                        CheckoutProductCard(product = product, cart = cart, userId = userId)
                    }

                    // Promo Code
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 12.dp)
                                .height(60.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .background(Color.White, shape = RoundedCornerShape(12.dp)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = "",
                                onValueChange = { /* TODO */ },
                                placeholder = {
                                    Text(
                                        text = "Have a promo code? Enter here",
                                        fontSize = 14.sp
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent
                                ),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )

                            Button(
                                onClick = { /* Apply promo code */ },
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .height(48.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFF1F1F1),
                                    contentColor = Color.Gray
                                )
                            ) {
                                Text("Apply")
                            }
                        }
                    }

                    // User Info
                    item {
                        CheckoutBottom(
                            userName = user?.displayName,
                            userPhoneNumber = user?.phoneNumber
                        )
                    }

                    // Checkout Button
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { /* TODO: Handle checkout */ },
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
                            Text(text = "Checkout $1615.4")
                        }
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CheckoutPR() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painterResource(id = R.drawable.giay_nike_air_zoom_structure_25_nam_den_trang_01),
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Nike", fontSize = 12.sp)
                        Icon(
                            Icons.Default.Verified,
                            contentDescription = "",
                            tint = Color.Blue,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Text(
                        text = "Nike Something Serious",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "5.000.678 đ",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(text = "Size: 32  ---  Quantity: 2", fontSize = 12.sp)

                }

                IconButton(
                    onClick = {},
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )

                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painterResource(id = R.drawable.giay_nike_air_zoom_structure_25_nam_den_trang_01),
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Nike", fontSize = 12.sp)
                        Icon(
                            Icons.Default.Verified,
                            contentDescription = "",
                            tint = Color.Blue,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Text(
                        text = "Nike Something Serious",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "5.000.678 đ",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(text = "Size: 32  ---  Quantity: 2", fontSize = 12.sp)

                }

                IconButton(
                    onClick = {},
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )

                }
            }
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Subtotal", fontSize = 14.sp)
                    Text(text = "$14.24", fontSize = 14.sp)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Shipping fee", fontSize = 14.sp)
                    Text(text = "$5", fontSize = 12.sp)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Shipping tax", fontSize = 14.sp)
                    Text(text = "$2", fontSize = 12.sp)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Order Total", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "$200", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
                HorizontalDivider()
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Payment Method", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Change", fontSize = 14.sp, color = Color.LightGray)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_google_logo),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .padding(5.dp)
                    )
                    Text(text = "ZaloPay", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Shipping Address", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Change", fontSize = 14.sp, color = Color.LightGray)
                }
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Nguyen Quang Dung",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            Icons.Default.Phone,
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Color.LightGray
                        )
                        Text(text = "0986492568", fontSize = 14.sp)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            Icons.Default.Person2,
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Color.LightGray
                        )
                        Text(
                            text = "82536 Timmy Coves, South Liana, Maine 87356, USA",
                            fontSize = 14.sp
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { /* TODO: Handle checkout */ },
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
            Text(text = "Checkout $1615.4")
        }
    }
}