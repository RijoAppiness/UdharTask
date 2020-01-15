package com.example.udhartask.network

import com.example.udhartask.models.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI{
        @GET("top-headlines")
        fun getNews(@Query("sources") sources:String,@Query("apiKey") apiKey:String): Call<Data>
}