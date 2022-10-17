package com.example.woloxapp.Service
import com.google.gson.annotations.SerializedName

data class UserResponse(
    val data: DataUserResponse
)
data class DataUserResponse(
    @SerializedName("id") val id: Int,
    val email: String,
    @SerializedName("provider") val provider: String,
    val uid: String,
    val allowPasswordChange: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("image") val image: String? = null
)
