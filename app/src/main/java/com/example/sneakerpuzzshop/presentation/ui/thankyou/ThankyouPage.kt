package com.example.sneakerpuzzshop.presentation.ui.thankyou

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.utils.others.PaymentResult
import com.example.sneakerpuzzshop.utils.others.formatCurrency
import com.example.sneakerpuzzshop.utils.others.startPayment

@Composable
fun ThankyouPage(modifier: Modifier = Modifier) {

}

@Preview
@Composable
fun ThankyouPagePR(modifier: Modifier = Modifier, onContinueShopping: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFE0F7FA), Color(0xFFB2EBF2))
                )
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.zlp),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Thank You!",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                )
                Text(
                    text = "Your payment was successful",
                    fontSize = 20.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "You’ll be redirected shortly or tap below to continue shopping.",
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onContinueShopping() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A50FF),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Continue Shopping", fontSize = 16.sp)
                }
            }
        }
    }
}
