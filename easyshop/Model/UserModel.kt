package com.example.easyshop.Model

data class UserModel(
    val name : String = "",
    val email : String = "",
    val uid : String = "",
    val cartItems : Map<String,Long> = emptyMap(),
    val wishlistItems : Map<String,Boolean> = emptyMap(),
    val address : String = ""
)
