package com.example.woloxapp.Service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET(COMMENTS)
    suspend fun getNews(@Query("page") page: Number): Response<NewsResponse>

    companion object {
        private const val COMMENTS = "/comments"
    }
}
