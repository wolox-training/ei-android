package com.example.woloxapp.ui.Home.tablayout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woloxapp.databinding.FragmentNewsBinding
import com.example.woloxapp.ui.Home.tablayout.adapters.NewsAdapter

class NewsFragment : Fragment() {
    private lateinit var _binding: FragmentNewsBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            recyclerNews.layoutManager = LinearLayoutManager(context)
            recyclerNews.adapter = NewsAdapter(NewsProvider.newsList)
            if (NewsProvider.newsList.isEmpty()) emptyNews.visibility =
                View.VISIBLE
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
            }
        }
    }
}
