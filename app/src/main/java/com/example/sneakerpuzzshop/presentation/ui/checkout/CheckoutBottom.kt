package com.example.sneakerpuzzshop.presentation.ui.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.utils.others.BillingResult
import com.example.sneakerpuzzshop.utils.others.formatCurrency
import com.example.sneakerpuzzshop.utils.ui.ROUTE_EDIT_PROFILE

@Composable
fun CheckoutBottom(
    userName: String?,
    userPhoneNumber: String?,
    userAddress: String?,
    billingResult: BillingResult,
    navController: NavHostController
) {
    var isShowDialog by remember { mutableStateOf(false) }

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
                Text(text = formatCurrency(billingResult.subtotal), fontSize = 14.sp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Shipping fee", fontSize = 14.sp)
                Text(text = formatCurrency(billingResult.shipping), fontSize = 12.sp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "VAT 10%", fontSize = 14.sp)
                Text(text = formatCurrency(billingResult.tax), fontSize = 12.sp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Order Total", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text(
                    text = formatCurrency(billingResult.total),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
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
                Text(
                    text = "Change",
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    modifier = Modifier.clickable { isShowDialog = true })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Image(
                    painterResource(id = R.drawable.zlp),
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
                Text(
                    text = "Change",
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    modifier = Modifier.clickable {
                        navController.navigate(ROUTE_EDIT_PROFILE)
                    })
            }
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "$userName",
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
                    if (userPhoneNumber?.isEmpty() == true) Text(
                        text = "To be updated",
                        fontSize = 14.sp
                    ) else Text(text = "$userPhoneNumber", fontSize = 14.sp)
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
                    if (userAddress?.isEmpty() == true) Text(
                        text = "To be updated",
                        fontSize = 14.sp
                    ) else
                    Text(
                        text = "$userAddress",
                        fontSize = 14.sp
                    )
                }
            }

        }
        if (isShowDialog == true) {
            AlertDialog(
                onDismissRequest = { isShowDialog == false },
                title = { Text(text = "Feature not available right now") },
                text = {
                    Text(text = "You can only pay with ZaloPay. Sorry for inconvenient")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            isShowDialog = false
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}