package com.example.udhartask.newsrepo

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.udhartask.R
import com.example.udhartask.models.Article
import com.example.udhartask.models.Data
import com.example.udhartask.network.API_KEY
import com.example.udhartask.network.NewsAPI
import com.example.udhartask.network.RetrofitClient
import com.example.udhartask.network.SOURCE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(val context: Application) {
    private val newsAPI = RetrofitClient.getInstance().create(NewsAPI::class.java)
    val errorMessage = MutableLiveData<String>()
    val observableArticles = MutableLiveData<List<Article>>()
    fun requestNews() {
        val call = newsAPI.getNews(SOURCE, API_KEY)
        call.enqueue(object : Callback<Data> {
            override fun onFailure(call: Call<Data>, t: Throwable) {
                errorMessage.postValue(context.getString(R.string.could_not_reach_server))
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    val newsList = response.body()?.articles
                    observableArticles.postValue(newsList)
                } else {
                    errorMessage.postValue(context.getString(R.string.server_response_error))
                }
            }

        })
    }
}