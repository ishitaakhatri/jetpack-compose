package com.example.mywishlistapp

sealed class screen(val route : String) {
    object HomeScreen : screen(route = "Home_Screen")
    object AddScreen : screen("Add_Screen")
}