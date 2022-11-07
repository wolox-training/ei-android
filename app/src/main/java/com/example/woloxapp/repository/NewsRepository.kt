package com.example.woloxapp.repository

import android.content.Context
import com.example.woloxapp.Service.*
import com.example.woloxapp.model.NewDetail
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

    suspend fun getNewDetail(id: Number): NetworkResponse<Response<NewDetail>> =
        withContext(Dispatchers.IO) {
            NetworkRequestHandler.safeApiCall { myNewsApi.getNewDetail(id) }
        }

    suspend fun updateLike(id: Int) = withContext(Dispatchers.IO) {
        NetworkRequestHandler.safeApiCall { myNewsApi.updateLike(id) }
    }
}
