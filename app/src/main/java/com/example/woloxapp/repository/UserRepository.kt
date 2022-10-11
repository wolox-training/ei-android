package com.example.woloxapp.repository

import com.example.woloxapp.Service.RetrofitClient
import com.example.woloxapp.Service.UserApi
import com.example.woloxapp.Service.UserResponse
import com.example.woloxapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository {
    private val myApi: UserApi = RetrofitClient().create(UserApi::class.java)
    suspend fun loginUser(user: User): Response<UserResponse> =
        withContext(Dispatchers.IO) {
            myApi.authenticateUser(user)
        }
}
