package com.example.sneakerpuzzshop.presentation.ui.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.domain.model.OrderModel
import com.example.sneakerpuzzshop.utils.others.ORDER_STATUS_LIST
import com.example.sneakerpuzzshop.utils.others.formatCurrency
import com.example.sneakerpuzzshop.utils.others.formatDateOnly
import com.example.sneakerpuzzshop.utils.ui.ROUTE_ORDER
import com.example.sneakerpuzzshop.utils.ui.ROUTE_ORDER_DETAILS

@Composable
fun OrderCard(modifier: Modifier = Modifier, order: OrderModel, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order No: ${order.orderId}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = formatDateOnly(order.date), fontSize = 16.sp, color = Color.Gray)
            }
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            color = Color.Gray,
                        )
                    ) { append(text = "Name: ") }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) { append(text = "${order.userName}") }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                color = Color.Gray,
                            )
                        ) { append(text = "Total items: ") }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) { append(text = "${order.items.size}") }
                    },
                )
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                color = Color.Gray,
                            )
                        ) { append(text = "Total amount: ") }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) { append(text = formatCurrency(order.billingResult.total)) }
                    },
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        navController.navigate(ROUTE_ORDER_DETAILS + order.orderId)
                    },
                    modifier = Modifier
                        .height(36.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(1.dp, color = Color.Black)
                ) {
                    Text("Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
                Text(
                    order.status,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when (order.status) {
                        ORDER_STATUS_LIST[0] -> Color.Yellow
                        ORDER_STATUS_LIST[1] -> Color.Green
                        ORDER_STATUS_LIST[2] -> Color.Cyan
                        else -> Color.Red
                    }
                )
            }
        }
    }
}