package com.mehedisoftdev.retrofitexample

import android.app.Application
import com.mehedisoftdev.retrofitexample.api.NewsServiceInterface
import com.mehedisoftdev.retrofitexample.api.RetrofitHelper
import com.mehedisoftdev.retrofitexample.repository.NewsRepository

class NewslyApplication: Application() {
    private lateinit var newsService: NewsServiceInterface
    lateinit var newsRepository: NewsRepository

    override fun onCreate() {
        super.onCreate()
        newsService = RetrofitHelper.getInstance().create(NewsServiceInterface::class.java)
        newsRepository = NewsRepository(newsService)
    }
}