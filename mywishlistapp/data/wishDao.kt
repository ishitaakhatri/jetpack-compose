package com.example.mywishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class wishDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addwish(wishEntity : wish)

    @Query("Select * from `wish-table`")
    abstract fun getAllWishes() : Flow<List<wish>>

    @Update
    abstract suspend fun updateAWish(wishEntity: wish)

    @Delete
    abstract suspend fun deleteAWish(wishEntity: wish)

    @Query("Select * from `wish-table` where id=:id")
    abstract fun getAWishByID(id : Long) : Flow<wish?>
}