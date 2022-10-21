package com.example.woloxapp.ui.Home.tablayout.fragments

import com.example.woloxapp.model.New

class NewsProvider {
    companion object {
        private const val title = "Ali Connors"
        private const val description = "I'll be in yout neighborhood doing errands ..."
        private const val image =
            "https://a.mktgcdn.com/p/10wXkuT6srqcGrdIKPpIu2Z4tmTPmW426btNA_fi7a8/2000x2000.png"
        private const val date = "15m"
        val newsList = listOf<New>(
            New(
                1,
                title,
                description,
                image,
                date,
                false
            ),
            New(
                2,
                title,
                description,
                image,
                date,
                false
            ),
            New(
                3,
                title,
                description,
                image,
                date,
                true
            ),
            New(
                4,
                title,
                description,
                image,
                date,
                false
            ),
            New(
                5,
                title,
                description,
                image,
                date,
                false
            ),
            New(
                6,
                title,
                description,
                image,
                date,
                false
            ),
            New(
                6,
                title,
                description,
                image,
                date,
                false
            )
        )
    }
}
