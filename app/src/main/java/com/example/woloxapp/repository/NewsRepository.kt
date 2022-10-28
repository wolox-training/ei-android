package com.example.woloxapp.repository

import android.content.Context
import com.example.woloxapp.Service.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsRepository(applicationContext: Context) {
    private val myNewsApi: NewsApi =
        RetrofitClient(applicationContext).create(NewsApi::class.java)

    suspend fun getNews(page: Number): NetworkResponse<Response<NewsResponse>> =
        withContext(Dispatchers.IO) {
            NetworkRequestHandler.safeApiCall { myNewsApi.getNews(page) }
        }
}
