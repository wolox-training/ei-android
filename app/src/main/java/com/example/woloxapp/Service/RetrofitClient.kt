package com.example.woloxapp.Service

import com.example.woloxapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
