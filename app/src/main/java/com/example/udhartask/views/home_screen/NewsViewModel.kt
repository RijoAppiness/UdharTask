package com.example.udhartask.views.home_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.udhartask.newsrepo.NewsRepository

class NewsViewModel(application: Application):AndroidViewModel(application){

    private val repository = NewsRepository(application)
    val observableNewsResponse = repository.observableArticles
    val observableErrorMessage = repository.errorMessage
    fun loadData() {
        repository.requestNews()
    }

}