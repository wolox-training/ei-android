package com.example.woloxapp.Service

import com.example.woloxapp.model.News

data class NewsResponse(
    val page: List<News>,
    val count: Int,
    val total_pages: Int,
    val total_count: Int,
    val current_page: Int,
    val previous_page: Int,
    val next_page: Int,
    val next_page_url: String,
    val previous_page_url: String
)
