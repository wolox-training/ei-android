package com.example.woloxapp.ui.Home.tablayout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.woloxapp.R
import com.example.woloxapp.model.New

class NewsAdapter(private val newsList: List<New>) :
    RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(
            layoutInflater.inflate(
                R.layout.item_new,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList[position]
        if (item.liked) holder.newLike.setImageResource(R.drawable.ic_like_on)
        holder.render(item)
    }

    override fun getItemCount(): Int = newsList.size
}
