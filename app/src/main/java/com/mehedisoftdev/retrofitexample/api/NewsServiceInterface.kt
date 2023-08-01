package com.mehedisoftdev.retrofitexample.api

import com.mehedisoftdev.retrofitexample.models.News
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "f597683faf4b4872b5b4a3b367bb55af"

interface NewsServiceInterface {
    @GET("v2/top-headlines?apiKey=$API_KEY")
   suspend fun getNewsHeadLines(@Query("country") country: String, @Query("page") page: Int): Response<News>
}