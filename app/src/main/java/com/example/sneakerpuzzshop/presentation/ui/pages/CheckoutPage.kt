package com.example.sneakerpuzzshop.presentation.ui.pages

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.example.sneakerpuzzshop.presentation.viewmodel.OrderViewModel
import com.example.sneakerpuzzshop.utils.others.BillingHelper
import com.example.sneakerpuzzshop.utils.others.PaymentResult
import com.example.sneakerpuzzshop.utils.others.formatCurrency
import com.example.sneakerpuzzshop.utils.others.startPayment
import com.example.sneakerpuzzshop.utils.ui.LoadingCircle

@SuppressLint("ContextCastToActivity")
@Composable
fun CheckoutPage(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    // Get user uid
    val userId = authViewModel.currentUser?.uid!!.toString()

    // State collector
    val userState by authViewModel.userInformation.collectAsState()
    val cartState by cartViewModel.cart.collectAsState()
    val productMap by cartViewModel.productDetailsMap.collectAsState()

    // Context and Activity
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    var promoCode by remember { mutableStateOf("") }
    var shippingFeeOverride by remember { mutableStateOf<Double?>(null) }
    var hasNavigatedToTYPage by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

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
                Log.d("OrderDebug", "Name: ${user?.name}, Phone: ${user?.phoneNumber}, Address: ${user?.address}")

                val showEmptyCartDialog = remember(
                    cartList,
                    hasNavigatedToTYPage
                ) { cartList.isEmpty() && !hasNavigatedToTYPage }

                val billingState = remember(cartList, productMap, shippingFeeOverride) {
                    BillingHelper.calculate(cartList, productMap, shippingFeeOverride)
                }

                if (showEmptyCartDialog) {
                    AlertDialog(
                        onDismissRequest = { },
                        title = {
                            Text(text = "Empty Cart", fontSize = 18.sp)
                        },
                        text = {
                            Text("You have no items in your cart. Please add items to continue checkout.")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    navController.popBackStack()
                                }
                            ) {
                                Text("Back to Cart")
                            }
                        }
                    )
                }

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
                                value = promoCode,
                                onValueChange = { promoCode = it },
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
                                onClick = {
                                    if (promoCode.trim()
                                            .equals("FREESHIPPING", ignoreCase = true)
                                    ) {
                                        shippingFeeOverride = 0.0
                                        showToast(
                                            context = context,
                                            message = "Coupon applied successfully"
                                        )
                                    } else {
                                        showToast(context = context, message = "Invalid coupon")
                                    }
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                },
                                enabled = promoCode.isNotEmpty(),
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .height(48.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (promoCode.isEmpty()) Color(0xFFF1F1F1) else Color(
                                        0xFF1A50FF
                                    ),
                                    contentColor = if (promoCode.isEmpty()) Color.Gray else Color.White
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
                            userAddress = user?.address,
                            billingResult = billingState,
                            navController = navController
                        )
                    }

                    // Checkout Button
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            enabled = (user?.phoneNumber?.isEmpty() == false || user?.address?.isEmpty() == false),
                            onClick = {
                                startPayment(
                                    activity = activity,
                                    amount = billingState.total.toInt().toString(),
                                ) { result ->
                                    when (result) {
                                        is PaymentResult.Canceled -> showToast(
                                            activity,
                                            "Đã hủy thanh toán"
                                        )

                                        is PaymentResult.Error -> showToast(
                                            activity,
                                            "Có lỗi xảy ra"
                                        )

                                        is PaymentResult.Success -> {
                                            showToast(activity, "Thanh toán thành công")
                                            hasNavigatedToTYPage = true
                                            orderViewModel.addToOrder(
                                                userId = userId,
                                                userPhoneNumber = user?.phoneNumber,
                                                userName = user?.name,
                                                userAddress = user?.address,
                                                cartItem = cartList,
                                                billingResult = billingState
                                            )
                                            cartViewModel.clearCart(userId)
                                            navController.navigate("thank_you") {
                                                popUpTo("checkout") { inclusive = true }
                                            }
                                        }
                                    }
                                }
                            },
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
                            Text(text = "Checkout ${formatCurrency(billingState.total)}")
                        }
                    }
                }

            }
        }
    }
}