package com.example.newnow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newnow.Api.RetrofitClient
import com.example.newnow.data.Article

class NewsViewModel : ViewModel() {
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    init {
        fetchTopNewsHeadlines()
    }

    fun fetchTopNewsHeadlines(category : String = "GENERAL") {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getTopHeadLines(category = category)
                response.articles.let {
                    _articles.postValue(it)
                }
            } catch (e: Exception) {
                Log.e("Retrofit Error", "API call failed", e)
            }
        }
    }
    fun fetchEveryThingWithQuery(Query : String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getEverything(query = Query)
                response.articles.let {
                    _articles.postValue(it)
                }
            } catch (e: Exception) {
                Log.e("Retrofit Error", "API call failed", e)
            }
        }
    }
}
