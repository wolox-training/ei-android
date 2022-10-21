package com.example.woloxapp.ui.Home.tablayout.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.woloxapp.R
import com.example.woloxapp.model.New

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val newTitle = view.findViewById<TextView>(R.id.tvNewsTitle)
    val newDescription = view.findViewById<TextView>(R.id.tvDescription)
    val newDate = view.findViewById<TextView>(R.id.tvDate)
    val newPhoto = view.findViewById<ImageView>(R.id.ivNews)
    val newLike = view.findViewById<ImageView>(R.id.ivLike)

    fun render(newsModel: New) {
        newTitle.text = newsModel.title
        newDescription.text = newsModel.description
        newDate.text = newsModel.date
        Glide.with(newPhoto.context).load(newsModel.image).into(newPhoto)
    }
}
