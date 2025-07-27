package com.example.newnow.data

data class newsResponse(
    val status: String,
    val totalResult : Int,
    val articles : List<Article>
)
