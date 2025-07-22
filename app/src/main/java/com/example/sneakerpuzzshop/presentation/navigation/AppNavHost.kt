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
import com.example.sneakerpuzzshop.presentation.ui.category.CategoryProductsPage
import com.example.sneakerpuzzshop.presentation.ui.pages.CheckoutPage
import com.example.sneakerpuzzshop.presentation.ui.product.ProductDetails
import com.example.sneakerpuzzshop.presentation.ui.profile.EditProfile
import com.example.sneakerpuzzshop.presentation.ui.profile.EditProfileField
import com.example.sneakerpuzzshop.presentation.ui.review.ProductReview
import com.example.sneakerpuzzshop.presentation.ui.splash.SplashScreen
import com.example.sneakerpuzzshop.presentation.ui.thankyou.ThankyouPage
import com.example.sneakerpuzzshop.utils.ui.ROUTE_CATEGORY_PRODUCTS
import com.example.sneakerpuzzshop.utils.ui.ROUTE_CHECKOUT
import com.example.sneakerpuzzshop.utils.ui.ROUTE_EDIT_PROFILE
import com.example.sneakerpuzzshop.utils.ui.ROUTE_EDIT_PROFILE_FIELD
import com.example.sneakerpuzzshop.utils.ui.ROUTE_FORGET_PW
import com.example.sneakerpuzzshop.utils.ui.ROUTE_HOME
import com.example.sneakerpuzzshop.utils.ui.ROUTE_LOGIN
import com.example.sneakerpuzzshop.utils.ui.ROUTE_PRODUCTS_DETAILS
import com.example.sneakerpuzzshop.utils.ui.ROUTE_PRODUCT_REVIEW
import com.example.sneakerpuzzshop.utils.ui.ROUTE_SIGNUP
import com.example.sneakerpuzzshop.utils.ui.ROUTE_SPLASH
import com.example.sneakerpuzzshop.utils.ui.ROUTE_THANK_YOU

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
        composable("$ROUTE_CATEGORY_PRODUCTS{categoryId}") {
            var categoryId = it.arguments?.getString("categoryId")
            CategoryProductsPage(modifier, categoryId?:"", navController)
        }
        composable("$ROUTE_PRODUCTS_DETAILS{productId}") {
            var productId = it.arguments?.getString("productId")
            ProductDetails(modifier, productId?:"", navController)
        }
        composable("$ROUTE_PRODUCTS_DETAILS{productId}$ROUTE_PRODUCT_REVIEW") {
            val productId = it.arguments?.getString("productId")
            ProductReview(modifier, productId?:"", navController)
        }
        composable(ROUTE_CHECKOUT) {
            CheckoutPage(navController)
        }
        composable(ROUTE_THANK_YOU) {
            ThankyouPage(navController = navController)
        }
        composable(ROUTE_EDIT_PROFILE) {
            EditProfile(navController = navController)
        }
        composable(ROUTE_EDIT_PROFILE_FIELD) {
            EditProfileField(navController = navController)
        }
    }
}