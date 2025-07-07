package com.example.sneakerpuzzshop.presentation.navigation

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
import com.example.sneakerpuzzshop.utils.ROUTE_FORGET_PW
import com.example.sneakerpuzzshop.utils.ROUTE_HOME
import com.example.sneakerpuzzshop.utils.ROUTE_LOGIN
import com.example.sneakerpuzzshop.utils.ROUTE_SIGNUP
import com.example.sneakerpuzzshop.utils.ROUTE_SPLASH

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {

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
            HomeScreen(navController)
        }
        composable(ROUTE_FORGET_PW) {
            ForgetPasswordScreen(viewModel = hiltViewModel(), navController)
        }

    }
}