package com.example.mywishlistapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [wish::class],
    version = 1,
    exportSchema = false
)
abstract class wishDatabase : RoomDatabase() {
    abstract fun wishDao(): wishDao
}