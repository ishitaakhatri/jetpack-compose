package com.example.easyshop

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easyshop.Screen.AuthScreen
import com.example.easyshop.Screen.HomeScreen
import com.example.easyshop.Screen.LoginScreen
import com.example.easyshop.Screen.SignupScreen
import com.example.easyshop.pages.CategoryProductsPage
import com.example.easyshop.pages.ProductDetailPage
import com.example.easyshop.pages.checkOutPage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    GlobalNavigation.navController = navController
    val isloggedin = Firebase.auth.currentUser!=null
    val firstpage = if (isloggedin) "home" else "auth"
    
    NavHost(navController = navController, startDestination = firstpage){
        composable("auth"){
            AuthScreen(modifier,navController)
        }
        composable("login"){
            LoginScreen(modifier,navController)
        }
        composable("signup"){
            SignupScreen(modifier,navController)
        }
        composable("home"){
            HomeScreen(modifier,navController)
        }
        composable("CategoryProducts/{categoryid}"){
            var categoryid = it.arguments?.getString("categoryid")
            CategoryProductsPage(categoryid)
        }
        composable("ProductDetails/{productid}"){
            var productid = it.arguments?.getString("productid")
            ProductDetailPage(productid)
        }

        composable("checkout"){
            var productid = it.arguments?.getString("productid")
            checkOutPage(productid)
        }
    }
}


object GlobalNavigation{
    lateinit var navController: NavHostController
}