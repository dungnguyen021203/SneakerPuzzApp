package com.example.sneakerpuzzshop.presentation.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sneakerpuzzshop.R
import com.example.sneakerpuzzshop.common.Resource

@Composable
fun HeaderView(modifier: Modifier = Modifier, viewModel: AuthViewModel = hiltViewModel()) {
    val authState by viewModel.userInformation.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserInformation()
    }

    var user = (authState as? Resource.Success)?.data

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                AsyncImage(
                    model = user?.avatar,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(5.dp)
                )
            }
            Column {
                Text(text = "Welcome Back")
                Text(
                    text = (if (user?.name?.isEmpty() == true) "HomeBoi" else user?.name).toString(),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = "Notification")
    }
}