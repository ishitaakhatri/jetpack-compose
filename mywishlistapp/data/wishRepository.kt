package com.example.mywishlistapp.data

import kotlinx.coroutines.flow.Flow


class wishRepository(private val wishDao: wishDao) {

    suspend fun addAWish(wish: wish){
        wishDao.addwish(wish)
    }

    fun getWishes() : Flow<List<wish>> = wishDao.getAllWishes()

    fun getAWishByID(id: Long): Flow<wish?> {
        return wishDao.getAWishByID(id)
    }

    suspend fun updateAWish(wish: wish){
        wishDao.updateAWish(wish)
    }
    suspend fun deleteAWish(wish: wish){
        wishDao.deleteAWish(wish)
    }
}