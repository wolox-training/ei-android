package com.example.woloxapp.ui.Home.tablayout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.woloxapp.R
import com.example.woloxapp.model.News

class NewsAdapter(
    private val newsList: MutableList<News>
) :
    RecyclerView.Adapter<NewsViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

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
            ),
            mListener
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

    interface OnItemClickListener {
        fun onItemClick(position: Int, newsList: News)
    }
}
