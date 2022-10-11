package com.example.woloxapp.model
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email") val email: String,
    val password: String
)
