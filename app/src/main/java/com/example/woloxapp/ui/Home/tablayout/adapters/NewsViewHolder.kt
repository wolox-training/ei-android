package com.example.woloxapp.ui.Home.tablayout.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.woloxapp.databinding.ItemNewBinding
import com.example.woloxapp.model.News
import com.example.woloxapp.utils.Constants
import org.ocpsoft.prettytime.PrettyTime
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class NewsViewHolder(
    view: View,
    private val listener: NewsAdapter.OnItemClickListener
) :
    RecyclerView.ViewHolder(view) {
    val binding = ItemNewBinding.bind(view)
    fun render(newsModel: News) {
        with(binding) {
            tvNewsTitle.text = newsModel.commenter
            tvDescription.text = newsModel.comment
            tvDate.text = PrettyTime().format(
                SimpleDateFormat(Constants.TIME_AGO_FORMAT, Locale.US).parse(
                    newsModel.created_at
                )?.time?.let {
                    println(it)
                    Date(
                        it
                    )
                }
            )
            Glide.with(ivNews.context).load(randomURLImage())
                .placeholder(ColorDrawable(Color.GRAY))
                .into(ivNews)
        }
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition, newsModel)
        }
    }

    private fun randomURLImage(): String {
        val randomNumberOne =
            BigDecimal(Math.random()).setScale(1, RoundingMode.HALF_EVEN)
        val randomNumberTwo =
            BigDecimal(Math.random()).setScale(1, RoundingMode.HALF_EVEN)
        return when {
            randomNumberOne > randomNumberTwo -> {
                ALTERNATIVE_IMAGE_URL
            }
            randomNumberOne == randomNumberTwo -> {
                SAD_IMAGE_URL
            }
            else -> {
                STATIC_IMAGE_URL
            }
        }
    }

    companion object {
        const val SAD_IMAGE_URL: String =
            "https://images.ole.com.ar/2022/10/23/Xc9Sh6Sf4_340x340__1.jpg"
        const val ALTERNATIVE_IMAGE_URL: String =
            "https://media.tenor.com/ycChCfbvwLcAAAAd/yoshi-dan%C3%A7ando.gif"
        const val STATIC_IMAGE_URL: String =
            "https://2.bp.blogspot.com/-sAi5fIHKhQ4/WFRHbHW2mNI/AAAAAAAACok/yHjUdAct1QICXvKWnamEQW1neZ_m0dDFwCLcB/s000/escudo-de-boca1.jpeg"
    }
}
