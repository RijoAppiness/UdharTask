package com.example.udhartask.network

import com.example.udhartask.models.Data
import retrofit2.Call
import retrofit2.http.*

interface TimeSubmissionAPI{
    @FormUrlEncoded
    @POST("time")
    fun sendTime(@Field("current-time") time:String): Call<Data>
}