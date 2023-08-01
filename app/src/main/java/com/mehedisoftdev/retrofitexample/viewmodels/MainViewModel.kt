package com.mehedisoftdev.retrofitexample.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehedisoftdev.retrofitexample.models.News
import com.mehedisoftdev.retrofitexample.repository.NewsRepository
import com.mehedisoftdev.retrofitexample.repository.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val newsRepository: NewsRepository): ViewModel() {

    // live data of news object
    val newsLiveData: LiveData<Resource<News>>
        get() = newsRepository.newsLiveData
    // fetch news head lines
    fun getNewsHeadLines(country: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.getNewsHeadLines(country, page)
        }
    }
}