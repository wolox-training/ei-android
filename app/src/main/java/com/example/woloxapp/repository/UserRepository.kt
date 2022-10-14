package com.example.woloxapp.repository

import com.example.woloxapp.Service.* // ktlint-disable no-wildcard-imports
import com.example.woloxapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository : BaseApiResponse() {
    private val myApi: UserApi = RetrofitClient().create(UserApi::class.java)

    suspend fun login(user: User): Flow<NetworkResult<UserResponse>> {
        return flow {
            emit(safeApiCall { myApi.authenticateUser(user) })
        }.flowOn(Dispatchers.IO)
    }
}
