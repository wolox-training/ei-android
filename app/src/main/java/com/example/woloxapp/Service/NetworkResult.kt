package com.example.woloxapp.Service

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T? = null) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    class Failure<T>(message: String) : NetworkResult<T>(null, message)
}
