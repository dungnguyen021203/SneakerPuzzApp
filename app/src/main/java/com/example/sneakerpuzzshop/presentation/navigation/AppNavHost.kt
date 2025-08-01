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
import com.example.sneakerpuzzshop.presentation.ui.category.CategoryProductsPage
import com.example.sneakerpuzzshop.presentation.ui.craft.FAQPage
import com.example.sneakerpuzzshop.presentation.ui.craft.Surprise
import com.example.sneakerpuzzshop.presentation.ui.home.HomeScreen
import com.example.sneakerpuzzshop.presentation.ui.order.OrderDetails
import com.example.sneakerpuzzshop.presentation.ui.order.OrderManagePage
import com.example.sneakerpuzzshop.presentation.ui.pages.CheckoutPage
import com.example.sneakerpuzzshop.presentation.ui.product.ProductDetails
import com.example.sneakerpuzzshop.presentation.ui.profile.EditProfile
import com.example.sneakerpuzzshop.presentation.ui.profile.EditProfileFieldAddress
import com.example.sneakerpuzzshop.presentation.ui.profile.EditProfileFieldName
import com.example.sneakerpuzzshop.presentation.ui.profile.EditProfileFieldPassword
import com.example.sneakerpuzzshop.presentation.ui.profile.EditProfileFieldPhone
import com.example.sneakerpuzzshop.presentation.ui.review.ProductReview
import com.example.sneakerpuzzshop.presentation.ui.search.SearchResults
import com.example.sneakerpuzzshop.presentation.ui.splash.SplashScreen
import com.example.sneakerpuzzshop.presentation.ui.thankyou.ThankyouPage
import com.example.sneakerpuzzshop.utils.ui.ROUTE_CATEGORY_PRODUCTS
import com.example.sneakerpuzzshop.utils.ui.ROUTE_CHECKOUT
import com.example.sneakerpuzzshop.utils.ui.ROUTE_EDIT_PROFILE
import com.example.sneakerpuzzshop.utils.ui.ROUTE_EDIT_PROFILE_FIELD_ADDRESS
import com.example.sneakerpuzzshop.utils.ui.ROUTE_EDIT_PROFILE_FIELD_NAME
import com.example.sneakerpuzzshop.utils.ui.ROUTE_EDIT_PROFILE_FIELD_PASSWORD
import com.example.sneakerpuzzshop.utils.ui.ROUTE_EDIT_PROFILE_FIELD_PHONE
import com.example.sneakerpuzzshop.utils.ui.ROUTE_FAQS
import com.example.sneakerpuzzshop.utils.ui.ROUTE_FORGET_PW
import com.example.sneakerpuzzshop.utils.ui.ROUTE_HOME
import com.example.sneakerpuzzshop.utils.ui.ROUTE_LOGIN
import com.example.sneakerpuzzshop.utils.ui.ROUTE_ORDER
import com.example.sneakerpuzzshop.utils.ui.ROUTE_ORDER_DETAILS
import com.example.sneakerpuzzshop.utils.ui.ROUTE_PRODUCTS_DETAILS
import com.example.sneakerpuzzshop.utils.ui.ROUTE_PRODUCT_REVIEW
import com.example.sneakerpuzzshop.utils.ui.ROUTE_SEARCH_RESULT
import com.example.sneakerpuzzshop.utils.ui.ROUTE_SIGNUP
import com.example.sneakerpuzzshop.utils.ui.ROUTE_SPLASH
import com.example.sneakerpuzzshop.utils.ui.ROUTE_SURPRISE
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
        composable(ROUTE_EDIT_PROFILE_FIELD_NAME) {
            EditProfileFieldName(navController = navController)
        }
        composable(ROUTE_EDIT_PROFILE_FIELD_PHONE) {
            EditProfileFieldPhone(navController = navController)
        }
        composable(ROUTE_EDIT_PROFILE_FIELD_ADDRESS) {
            EditProfileFieldAddress(navController = navController)
        }
        composable(ROUTE_EDIT_PROFILE_FIELD_PASSWORD) {
            EditProfileFieldPassword(navController = navController)
        }
        composable("$ROUTE_ORDER{orderStatus}") {
            var orderStatus = it.arguments?.getString("orderStatus")
            OrderManagePage(modifier, orderStatus?:"", navController)
        }
        composable("$ROUTE_ORDER_DETAILS{orderId}") {
            var orderId = it.arguments?.getString("orderId")
            OrderDetails(modifier, orderId?:"", navController)
        }
        composable(ROUTE_FAQS) {
            FAQPage(navController = navController)
        }
        composable(ROUTE_SURPRISE) {
            Surprise(navController = navController)
        }
        composable("$ROUTE_SEARCH_RESULT{query}") {
            var query = it.arguments?.getString("query") ?: ""
            SearchResults(navController = navController, initialQuery = query)
        }
    }
}