package com.example.woloxapp.model

data class News(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val date: String,
    val liked: Boolean
)
