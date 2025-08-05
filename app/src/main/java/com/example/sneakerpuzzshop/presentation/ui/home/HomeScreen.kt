package com.example.sneakerpuzzshop.presentation.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.sneakerpuzzshop.presentation.components.showToast
import com.example.sneakerpuzzshop.presentation.ui.pages.CartPage
import com.example.sneakerpuzzshop.presentation.ui.pages.FavoritePage
import com.example.sneakerpuzzshop.presentation.ui.pages.HomePage
import com.example.sneakerpuzzshop.presentation.ui.pages.ProfilePage

@SuppressLint("ContextCastToActivity")
@Composable
fun HomeScreen(navController: NavHostController) {

    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    val navItemList = listOf<NavItem>(
        NavItem("Home", Icons.Default.Home),
        NavItem("Favorite", Icons.Default.Favorite),
        NavItem("Cart", Icons.Default.ShoppingCart),
        NavItem("Profile", Icons.Default.Person2)
    )

    var selectedIndex by rememberSaveable {
        mutableStateOf(0)
    }

    var backPressedTime by remember { mutableStateOf(0L) }

    BackHandler(enabled = true) {
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            activity?.finish()
        } else {
            showToast(context = context, message = "Nhấn lần nữa để thoát")
            backPressedTime = System.currentTimeMillis()
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) {
        ContentScreen(modifier = Modifier.padding(it), selectedIndex, navController = navController)
    }
}

@Composable
fun ContentScreen(modifier: Modifier, index: Int, navController: NavHostController) {
    when(index) {
        0 -> HomePage(modifier, navController = navController)
        1 -> FavoritePage(modifier, navController = navController)
        2 -> CartPage(modifier, navController = navController)
        3 -> ProfilePage(modifier, navController = navController)
    }
}

data class NavItem(
    val label: String,
    val icon: ImageVector
)