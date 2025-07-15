package com.example.mywishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wish-table")
data class wish(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title : String = "",
    @ColumnInfo(name = "wish-desc")
    val description : String = ""
)


object  DummyWish{
    val wishlist = listOf(
        wish(1,"google watch 2","an android watch"),
        wish(2,"helicopter","because helicopter can make me feel special"),
        wish(3,"car","long cars are always sexy"),
        wish(4,"doggy","because i love dogs so much")
    )

}

