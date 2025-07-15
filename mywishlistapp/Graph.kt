package com.example.mywishlistapp

import android.content.Context
import androidx.room.Room
import com.example.mywishlistapp.data.wishDatabase
import com.example.mywishlistapp.data.wishRepository

object Graph {

    lateinit var database: wishDatabase

    val wishRepository by lazy{
        wishRepository(wishDao = database.wishDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context,wishDatabase::class.java,name = "wishlist.db").build()
    }
}