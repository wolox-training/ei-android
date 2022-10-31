package com.example.woloxapp.Service

import com.example.woloxapp.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST(API_LOGIN)
    suspend fun authenticateUser(@Body user: User): Response<UserResponse>
    companion object {
        const val API_LOGIN = "/auth/sign_in"
    }
}
