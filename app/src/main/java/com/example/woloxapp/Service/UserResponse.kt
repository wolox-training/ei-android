package com.example.woloxapp.Service
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Int,
    val email: String,
    @SerializedName("name") val name: String
)
