package com.example.newnow.Api

import com.example.newnow.data.newsResponse
import com.kwabenaberko.newsapilib.models.response.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewApiService {
    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("apiKey") apiKey: String = "fed2c17da22c4632be1adb6020a4b21e",
        @Query("country") country: String = "us",
        @Query("language") language: String = "en",
        @Query("category") category: String = "general",
        @Query("q") query: String? = null
    ): newsResponse

    @GET("everything")
    suspend fun getEverything(
        @Query("apiKey") apiKey: String = "fed2c17da22c4632be1adb6020a4b21e",
        @Query("q") query: String? = null,
        @Query("language") language: String = "en"
    ): newsResponse

    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = "fed2c17da22c4632be1adb6020a4b21e",
        @Query("language") language: String = "en",
        @Query("country") country: String = "us"
    ): SourcesResponse
}
