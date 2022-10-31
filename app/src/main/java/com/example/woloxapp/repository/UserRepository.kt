package com.example.woloxapp.repository

import com.example.woloxapp.Service.* // ktlint-disable no-wildcard-imports
import com.example.woloxapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository {
    private val myApi: UserApi = RetrofitClient().create(UserApi::class.java)

    suspend fun login(user: User): NetworkResponse<Response<UserResponse>> =
        withContext(Dispatchers.IO) {
            NetworkRequestHandler.safeApiCall { myApi.authenticateUser(user) }
        }
}
