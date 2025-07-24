package com.example.sneakerpuzzshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sneakerpuzzshop.presentation.navigation.AppNavHost
import com.example.sneakerpuzzshop.presentation.theme.SneakerPuzzShopTheme
import com.example.sneakerpuzzshop.presentation.viewmodel.ThemeViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import vn.zalopay.sdk.ZaloPaySDK
import androidx.compose.runtime.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("AUTH", "Current user: ${FirebaseAuth.getInstance().currentUser?.email}")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }

            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isDarkMode by themeViewModel.dark.collectAsState()

            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                content = {
                    SneakerPuzzShopTheme(darkTheme = isDarkMode) {
                        AppNavHost()
                    }
                }
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
    }
}
