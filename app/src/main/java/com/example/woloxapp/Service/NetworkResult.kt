package com.example.woloxapp.Service

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(private val response: T) : NetworkResult<T>(response)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T> : NetworkResult<T>()
    class NotConnection<T>(message: String) : NetworkResult<T>(null, message)
}
