package com.example.woloxapp.Service

import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        val response = try {
            apiCall.invoke()
        } catch (e: java.lang.Exception) {
            val message = if (e is ConnectException || e is UnknownHostException) CONNECTION_ERROR else GENERIC_ERROR
            return NetworkResult.Failure(message)
        }
        try {
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(response.body())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("$INVALID_CREDENTIALS, $errorMessage")

    companion object {
        const val CONNECTION_ERROR = "Connection Error"
        const val GENERIC_ERROR = "Something wrong, please try again."
        const val INVALID_CREDENTIALS = "Invalid credentials"
    }
}
