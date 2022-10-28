package com.example.woloxapp.ui.Home.tablayout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.woloxapp.R
import com.example.woloxapp.model.News
import java.util.*

class NewsAdapter(private val newsList: MutableList<News>) :
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
        fun setImage(drawable: Int) {
            holder.binding.ivLike.setImageResource(drawable)
        }
        if (item.likes.isNotEmpty()) setImage(R.drawable.ic_like_on) else setImage(
            R.drawable.ic_like_off
        )
        holder.render(item)
    }

    override fun getItemCount(): Int = newsList.size
    fun moreNews(newsItems: List<News>) {
        newsList.addAll(newsItems)
        notifyDataSetChanged()
    }
}
