package com.example.woloxapp.repository

import android.content.Context
import com.example.woloxapp.Service.* // ktlint-disable no-wildcard-imports
import com.example.woloxapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository(applicationContext: Context) {
    private val myApi: UserApi = RetrofitClient(applicationContext).create(UserApi::class.java)

    suspend fun login(user: User): NetworkResponse<Response<UserResponse>> =
        withContext(Dispatchers.IO) {
            NetworkRequestHandler.safeApiCall { myApi.authenticateUser(user) }
        }
}
