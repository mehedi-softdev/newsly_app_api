package com.mehedisoftdev.retrofitexample.views

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehedisoftdev.retrofitexample.NewslyApplication
import com.mehedisoftdev.retrofitexample.R
import com.mehedisoftdev.retrofitexample.adapters.NewsAdapter
import com.mehedisoftdev.retrofitexample.api.NewsServiceInterface
import com.mehedisoftdev.retrofitexample.api.RetrofitHelper
import com.mehedisoftdev.retrofitexample.databinding.ActivityMainBinding
import com.mehedisoftdev.retrofitexample.models.Article
import com.mehedisoftdev.retrofitexample.models.News
import com.mehedisoftdev.retrofitexample.repository.NewsRepository
import com.mehedisoftdev.retrofitexample.repository.Resource
import com.mehedisoftdev.retrofitexample.viewmodels.MainViewModel
import com.mehedisoftdev.retrofitexample.viewmodels.MainViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter = NewsAdapter(this@MainActivity)
        binding.newsListRecyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(this@MainActivity)
        binding.newsListRecyclerView.layoutManager = layoutManager
        binding.newsListRecyclerView.setHasFixedSize(true)
        adapter.submitList(emptyList<Article>())

        val newsRepository = (application as NewslyApplication).newsRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(newsRepository))[MainViewModel::class.java]

        mainViewModel.newsLiveData.observe(this, Observer { resource ->
            when(resource) {
                is Resource.Loading -> {
                    Log.d("DEBUG", "Telling from loading state")
                    binding.progressBar.visibility = View.VISIBLE
                    binding.newsListRecyclerView.visibility = View.GONE
                }
                is Resource.Success -> {
                    Log.d("DEBUG", "Telling from success state")
                    resource.data?.let {
                        binding.progressBar.visibility = View.GONE
                        binding.newsListRecyclerView.visibility = View.VISIBLE
                        adapter.submitList(it.articles)
                    }

                }
                is Resource.Error -> {
                    Log.d("DEBUG", "Telling from error state")
                    Toast.makeText(applicationContext, "${resource.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        mainViewModel.getNewsHeadLines("us", 1)


    }

}