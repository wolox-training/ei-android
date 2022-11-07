package com.example.woloxapp.ui.Home.tablayout.fragments.NewDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.woloxapp.Service.NetworkResponse
import com.example.woloxapp.model.NewDetail
import com.example.woloxapp.repository.NewsRepository
import kotlinx.coroutines.launch

class NewDetailViewModel(val app: Application) : AndroidViewModel(app) {
    private val newsRepository: NewsRepository =
        NewsRepository(app.applicationContext)

    private val _newDetailResponse = MutableLiveData<NewDetail>(null)
    val newsDetailResponse: LiveData<NewDetail>
        get() = _newDetailResponse

    private val _updateLikeOk =
        MutableLiveData<ResponseStatus>(null)
    val updateLikeOk: LiveData<ResponseStatus>
        get() = _updateLikeOk

    private val _newDetailOk =
        MutableLiveData<ResponseStatus>(null)
    val newDetailOk: LiveData<ResponseStatus>
        get() = _newDetailOk

    fun getNewDetail(id: Number) {
        viewModelScope.launch {
            when (
                val responseNewDetailRepository =
                    newsRepository.getNewDetail(id)
            ) {
                is NetworkResponse.Success -> {
                    _newDetailResponse.value =
                        responseNewDetailRepository.response.body()
                    _newDetailOk.value = ResponseStatus.NewDetailOk
                }
                is NetworkResponse.Error -> {
                    _newDetailOk.value = ResponseStatus.NewDetailError
                }
                is NetworkResponse.Failure -> {
                    _newDetailOk.value = ResponseStatus.NewDetailFailure
                }
            }
        }
    }

    fun updateLike(id: Int) {
        viewModelScope.launch {
            when (newsRepository.updateLike(id)) {
                is NetworkResponse.Success -> {
                    getNewDetail(id)
                    _updateLikeOk.value = ResponseStatus.UpdateLikeOk
                }
                is NetworkResponse.Error -> {
                    _updateLikeOk.value = ResponseStatus.UpdateLikeError
                }
                is NetworkResponse.Failure -> {
                    _updateLikeOk.value = ResponseStatus.UpdateLikeFailure
                }
            }
        }
    }

    enum class ResponseStatus {
        NewDetailOk,
        NewDetailFailure,
        NewDetailError,
        UpdateLikeOk,
        UpdateLikeFailure,
        UpdateLikeError
    }
}
