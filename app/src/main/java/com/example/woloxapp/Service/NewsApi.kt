package com.example.woloxapp.Service

import com.example.woloxapp.model.NewDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {
    @GET(COMMENTS)
    suspend fun getNews(@Query("page") page: Number): Response<NewsResponse>

    @GET(NEW_DETAIL)
    suspend fun getNewDetail(@Path("id") id: Number): Response<NewDetail>

    @PUT(UPDATE_LIKE)
    suspend fun updateLike(@Path("id") id: Number): Response<NewDetail>

    companion object {
        private const val COMMENTS = "/comments"
        private const val NEW_DETAIL = "/comments/{id}"
        private const val UPDATE_LIKE = "/comments/like/{id}"
    }
}
