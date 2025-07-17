package com.example.sneakerpuzzshop.presentation.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.ui.checkout.CheckoutBottom
import com.example.sneakerpuzzshop.presentation.ui.checkout.CheckoutProductCard
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.CartViewModel
import com.example.sneakerpuzzshop.utils.LoadingCircle

@Composable
fun CheckoutPage(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val userId = authViewModel.currentUser?.uid!!.toString()
    val userState by authViewModel.userInformation.collectAsState()
    val cartState by cartViewModel.cart.collectAsState()
    val productMap by cartViewModel.productDetailsMap.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(userId) {
        cartViewModel.getCartFromUser(userId)
        authViewModel.getUserInformation()
    }

    var user = (userState as? Resource.Success)?.data

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
                            userName = user?.name,
                            userPhoneNumber = user?.phoneNumber,
                            userAddress = user?.address
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