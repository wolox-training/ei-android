package com.example.woloxapp.ui.Home.tablayout.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.woloxapp.NewDetailActivity
import com.example.woloxapp.databinding.FragmentNewsBinding
import com.example.woloxapp.model.News
import com.example.woloxapp.ui.Home.tablayout.adapters.NewsAdapter
import com.example.woloxapp.utils.Constants
import com.example.woloxapp.utils.ToastUtil

class NewsFragment : Fragment() {
    private lateinit var _binding: FragmentNewsBinding
    private lateinit var newsViewModel: NewsViewModel
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
        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        with(binding) {
            recyclerNews.layoutManager = LinearLayoutManager(context)
            recyclerNews.adapter =
                NewsAdapter(mutableListOf())
            newsViewModel.getData(false)
            newsViewModel.newsResponse.observe(viewLifecycleOwner) {
                it?.let {
                    (recyclerNews.adapter as NewsAdapter).moreNews(it.page)
                    (recyclerNews.adapter as NewsAdapter).notifyDataSetChanged()
                }
                recyclerNews.addOnScrollListener(
                    InnerScrollObserver()
                )
                newsViewModel.newListOk.observe(viewLifecycleOwner) {
                    if (it != null) binding.progressBarNews.visibility =
                        View.INVISIBLE
                    when (it) {
                        NewsViewModel.ResponseStatus.NewListOk -> {
                            binding.progressAddedNews.visibility =
                                View.INVISIBLE
                            showEmptyTextView(
                                false
                            )
                        }
                        NewsViewModel.ResponseStatus.NewListError,
                        NewsViewModel.ResponseStatus.NewListFailure -> newListErrorMessages()
                        null ->
                            binding.progressBarNews.visibility =
                                View.VISIBLE
                    }
                }
                swipeRefresh.setOnRefreshListener {
                    newsViewModel.getData(true)
                    newsViewModel.newListOk.observe(viewLifecycleOwner) {
                        swipeRefresh.isRefreshing = it == null
                    }
                }
            }
            newsViewModel.isCantScroll.observe(viewLifecycleOwner) {
                if (it) {
                    binding.progressAddedNews.visibility =
                        View.VISIBLE
                    newsViewModel.getData(false)
                }
            }
            (recyclerNews.adapter as NewsAdapter).setOnItemClickListener(object :
                    NewsAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int, news: News) {
                        val intent = Intent(
                            requireContext(),
                            NewDetailActivity::class.java
                        )
                        requireContext().startActivity(
                            intent.putExtra(
                                Constants.NEW_ID,
                                news.id
                            )
                        )
                    }
                })
        }
    }

    inner class InnerScrollObserver : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(
            recyclerView: RecyclerView,
            newState: Int
        ) {
            super.onScrollStateChanged(recyclerView, newState)
            invokeGetMoreData()
        }
    }

    private fun invokeGetMoreData() {
        with(binding) {
            val cantScroll = newsViewModel.isLoading.value == true &&
                !recyclerNews.canScrollVertically(CAN_SCROLL_VERTICALLY_REFERENCE)
            newsViewModel.isCantScroll.value = cantScroll
        }
    }

    private fun showEmptyTextView(boolean: Boolean) {
        if (boolean) binding.emptyNews.visibility = View.VISIBLE
        else binding.emptyNews.visibility = View.INVISIBLE
    }

    private fun newListErrorMessages() {
        showEmptyTextView(true)
        ToastUtil().showToast(
            CONNECTION_ERROR,
            requireContext()
        )
    }

    companion object {
        const val CONNECTION_ERROR: String =
            "Couldn't load news right now, try again"
        const val CAN_SCROLL_VERTICALLY_REFERENCE: Int = 1
    }
}
