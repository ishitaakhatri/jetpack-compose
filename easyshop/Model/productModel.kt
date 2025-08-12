package com.example.easyshop.Model

data class productModel(
    val id : String = "",
    val title : String = "",
    val description : String = "",
    val category : String = "",
    val price : String = "",
    val actualprice : String = "",
    val images : List<String> = emptyList(),
    )
