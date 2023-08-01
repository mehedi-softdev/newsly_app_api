package com.mehedisoftdev.retrofitexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mehedisoftdev.retrofitexample.api.NewsServiceInterface
import com.mehedisoftdev.retrofitexample.models.Article
import com.mehedisoftdev.retrofitexample.models.News

class NewsRepository(private val newsService: NewsServiceInterface) {
    private var newsMutableLiveData = MutableLiveData<Resource<News>>()
    val newsLiveData: LiveData<Resource<News>>
        get() = newsMutableLiveData

    suspend fun getNewsHeadLines(country: String, page: Int) {
        newsMutableLiveData.postValue(Resource.Loading()) // telling it is loading state

        try {
            val result = newsService.getNewsHeadLines(country, page)
            if(result?.body() != null) {
                val news: News = result.body()!!
                newsMutableLiveData.postValue(Resource.Success(news))
            }
        }catch (e: Exception) {
            newsMutableLiveData.postValue(Resource.Error(e.message.toString()))
        }
    }
}