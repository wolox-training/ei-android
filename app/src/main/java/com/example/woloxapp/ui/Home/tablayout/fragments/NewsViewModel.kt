package com.example.woloxapp.ui.Home.tablayout.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.woloxapp.Service.NetworkResponse
import com.example.woloxapp.Service.NewsResponse
import com.example.woloxapp.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(val app: Application) : AndroidViewModel(app) {
    private val newsRepository: NewsRepository =
        NewsRepository(app.applicationContext)
    private val _newsResponse = MutableLiveData<NewsResponse>(null)
    val newsResponse: LiveData<NewsResponse>
        get() = _newsResponse

    private val _newListOk = MutableLiveData<ResponseStatus>(null)
    val newListOk: LiveData<ResponseStatus>
        get() = _newListOk

    private val _currentPage = MutableLiveData<Number>(1)

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isCantScroll = MutableLiveData<Boolean>(false)
    val isCantScroll: MutableLiveData<Boolean>
        get() = _isCantScroll

    fun getData(firstPage: Boolean?) {
        viewModelScope.launch {
            _isLoading.value = false
            if (firstPage == true) _currentPage.value = 1
            when (
                val responseRepository =
                    _currentPage.value?.let { newsRepository.getNews(it) }
            ) {
                is NetworkResponse.Success -> {
                    _newsResponse.value = responseRepository.response.body()
                    _currentPage.value =
                        responseRepository.response.body()?.next_page
                    _newListOk.value = ResponseStatus.NewListOk
                    _isLoading.value = true
                }
                is NetworkResponse.Error -> {
                    _newListOk.value = ResponseStatus.NewListError
                }
                is NetworkResponse.Failure -> {
                    _newListOk.value = ResponseStatus.NewListFailure
                }
            }
        }
    }

    enum class ResponseStatus {
        NewListOk,
        NewListFailure,
        NewListError
    }
}
