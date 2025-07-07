package com.example.sneakerpuzzshop.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sneakerpuzzshop.presentation.ui.auth.ForgetPasswordScreen
import com.example.sneakerpuzzshop.presentation.ui.auth.LoginScreen
import com.example.sneakerpuzzshop.presentation.ui.auth.SignupScreen
import com.example.sneakerpuzzshop.presentation.ui.home.HomeScreen
import com.example.sneakerpuzzshop.presentation.ui.splash.SplashScreen
import com.example.sneakerpuzzshop.presentation.viewmodel.AuthViewModel
import com.example.sneakerpuzzshop.utils.*

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {

    GlobalNavigation.navController = navController

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_SPLASH) {
            SplashScreen(viewModel = hiltViewModel(), navController)
        }
        composable(ROUTE_LOGIN) {
            LoginScreen(viewModel = hiltViewModel(), navController)
        }
        composable(ROUTE_SIGNUP) {
            SignupScreen(viewModel = hiltViewModel(), navController)
        }
        composable(ROUTE_HOME) {
            HomeScreen(viewModel = hiltViewModel(), navController)
        }
        composable(ROUTE_FORGET_PW) {
            ForgetPasswordScreen(viewModel = hiltViewModel(), navController)
        }

    }
}

object GlobalNavigation {
    @SuppressLint("StaticFieldLeak")
    lateinit var navController: NavHostController
}