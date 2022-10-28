package com.example.woloxapp.Service

import android.content.Context
import android.content.SharedPreferences
import com.example.woloxapp.BuildConfig
import com.example.woloxapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(private val applicationContext: Context) {
    private val okHttpClient: OkHttpClient
        get() {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
            }
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor(applicationContext))
                .build()
        }

    fun <T> create(serviceInterface: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(serviceInterface)
    }

    private fun headerInterceptor(applicationContext: Context): Interceptor {
        val sharedPreferencesSaved: SharedPreferences? =
            applicationContext.getSharedPreferences(
                Constants.SHARED_PREFERENCES_USERNAME,
                Context.MODE_PRIVATE
            )
        val accessToken =
            sharedPreferencesSaved?.getString(
                Constants.ACCESS_TOKEN,
                Constants.emptyString
            )
        val client = sharedPreferencesSaved?.getString(
            Constants.CLIENT,
            Constants.emptyString
        )
        val uid = sharedPreferencesSaved?.getString(
            Constants.UID,
            Constants.emptyString
        )
        return Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder()
                .addHeader(Constants.ACCESS_TOKEN_HEADERS, accessToken)
                .addHeader(Constants.CLIENT_HEADERS, client)
                .addHeader(Constants.UID_HEADERS, uid)
                .build()
            chain.proceed(request)
        }
    }
}
