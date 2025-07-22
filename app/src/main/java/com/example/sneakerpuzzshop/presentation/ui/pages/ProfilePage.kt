package com.example.sneakerpuzzshop.presentation.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpCenter
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.common.Resource
import com.example.sneakerpuzzshop.presentation.components.OrderItem
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.utils.ui.LoadingCircle
import com.example.sneakerpuzzshop.utils.ui.ROUTE_EDIT_PROFILE
import com.example.sneakerpuzzshop.utils.ui.ROUTE_HOME
import com.example.sneakerpuzzshop.utils.ui.ROUTE_LOGIN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val authState by viewModel.userInformation.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getUserInformation()
    }

    authState.let {
        when (it) {
            is Resource.Failure -> {
                LaunchedEffect(authState) {
                    showToast(context = context, message = it.exception.message.toString())
                }
            }

            is Resource.Loading -> {
                LoadingCircle()
            }

            is Resource.Success<*> -> {
                var user = (authState as? Resource.Success)?.data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.systemBars.asPaddingValues())
                        .padding(horizontal = 16.dp)
                        .padding(top = 25.dp),
                    contentPadding = PaddingValues(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {

                    item {
                        Text(
                            text = "Profile",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6E6E6E)
                        )
                    }

                    item {
                        Card(
                            shape = RoundedCornerShape(100.dp),
                            elevation = CardDefaults.cardElevation(6.dp),
                            colors = CardDefaults.cardColors(Color.White)
                        ) {
                            if(user?.avatar?.isBlank() == true) {
                                Image(
                                    painterResource(R.drawable.pro5),
                                    contentDescription = "Profile image",
                                    modifier = Modifier
                                        .size(130.dp)
                                        .padding(8.dp)
                                )
                            } else {
                                AsyncImage(
                                    model = user?.avatar,
                                    contentDescription = "Profile image",
                                    modifier = Modifier
                                        .size(130.dp)
                                        .padding(8.dp)
                                )
                            }
                        }
                    }

                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = user?.name.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6E6E6E)
                            )
                            Text(
                                text = "Bronze", // TODO()
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    item {
                        Text(
                            text = "My Orders",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OrderItem(
                                icon = Icons.Default.AccountBalanceWallet,
                                label = "Pending",
                                color = Color.Blue,
                                modifier = Modifier.weight(1f)
                            )
                            OrderItem(
                                icon = Icons.Default.LocalShipping,
                                label = "Delivered",
                                color = Color(0xFFFFC107),
                                modifier = Modifier.weight(1f)
                            )
                            OrderItem(
                                icon = Icons.Default.ShoppingCart,
                                label = "Processing",
                                color = Color(0xFFFF66C4),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OrderItem(
                                icon = Icons.Default.Receipt,
                                label = "Cancelled",
                                color = Color.Green,
                                modifier = Modifier.weight(1f)
                            )
                            OrderItem(
                                icon = Icons.Default.Favorite,
                                label = "Wishlist",
                                color = Color.Red,
                                modifier = Modifier.weight(1f)
                            )
                            OrderItem(
                                icon = Icons.AutoMirrored.Filled.HelpCenter,
                                label = "Support",
                                color = Color(0XFFCB6CE6),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Profile option
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color(0xFF6E6E6E)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Edit Profile",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    navController.navigate(ROUTE_EDIT_PROFILE)
                                })
                        }
                    }

                    // Logout
                    item {
                        TextButton(
                            onClick = {
                                viewModel.signOut()
                                navController.navigate(ROUTE_LOGIN) {
                                    popUpTo(ROUTE_HOME) { inclusive = true }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.Logout,
                                    contentDescription = null,
                                    tint = Color(0xFF6E6E6E)
                                )
                                Text(text = "Sign Out", fontSize = 16.sp, color = Color(0xFF6E6E6E))
                            }
                        }
                    }
                }
            }
        }
    }
}