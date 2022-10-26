package com.example.woloxapp.ui.Home.tablayout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.woloxapp.databinding.FragmentNewsBinding
import com.example.woloxapp.ui.Home.tablayout.adapters.NewsAdapter
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
            recyclerNews.adapter = NewsAdapter(mutableListOf())
            newsViewModel.getData(false)
            newsViewModel.newsResponse.observe(viewLifecycleOwner) {
                it?.let {
                    (recyclerNews.adapter as NewsAdapter).moreNews(it.page)
                    (recyclerNews.adapter as NewsAdapter).notifyDataSetChanged()
                }
            }
            recyclerNews.addOnScrollListener(
                InnerScrollObserver() {
                    newsViewModel.getData(false)
                }
            )
            newsViewModel.newListOk.observe(viewLifecycleOwner) {
                if (it != null) binding.progressBarNews.visibility =
                    View.INVISIBLE
                when (it) {
                    NewsViewModel.ResponseStatus.NewListOk -> showEmptyTextView(
                        false
                    )
                    NewsViewModel.ResponseStatus.NewListError,
                    NewsViewModel.ResponseStatus.NewListFailure -> newListErrorMessages()
                    null -> binding.progressBarNews.visibility = View.VISIBLE
                }
            }
            swipeRefresh.setOnRefreshListener {
                newsViewModel.getData(true)
                newsViewModel.newListOk.observe(viewLifecycleOwner) {
                    swipeRefresh.isRefreshing = it == null
                }
            }
        }
    }

    inner class InnerScrollObserver(
        private val getMoreData: () -> Unit
    ) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            childCount = recyclerView.childCount
            totalCount = recyclerView.layoutManager!!.itemCount
            firstItem =
                (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            getNews =
                !loadingNews && (totalCount - childCount) <= (firstItem + visiblePort)
            when (getNews) {
                false -> if (totalCount > lastTotal) {
                    loadingNews = false
                    lastTotal = totalCount
                }
                true -> {
                    getMoreData.invoke()
                    loadingNews = true
                }
            }
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
        const val visiblePort = 8
        const val CONNECTION_ERROR = "Couldn't load news right now, try again"
        var lastTotal = 0
        var firstItem = 0
        var childCount = 0
        var totalCount = 0
        var loadingNews = true
        var getNews = false
    }
}
