package com.example.sneakerpuzzshop.presentation.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sneakerpuzzshop.presentation.navigation.GlobalNavigation
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.utils.ROUTE_HOME
import com.example.sneakerpuzzshop.utils.ROUTE_LOGIN
import com.example.sneakerpuzzshop.utils.ROUTE_SPLASH

@Composable
fun ProfilePage( modifier: Modifier = Modifier, viewModel: AuthViewModel = hiltViewModel()) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Something")
        TextButton(
            onClick = {
                viewModel.signOut()
                val navController = GlobalNavigation.navController
                navController.navigate(ROUTE_LOGIN) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign out", fontSize = 18.sp)
        }
    }
}