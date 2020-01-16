package com.example.udhartask.bgservice

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.udhartask.models.Data
import com.example.udhartask.network.RetrofitClient
import com.example.udhartask.network.TimeSubmissionAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * This is a worker class for uploading time. This class is scheduled from NewsViewActivity class
 */


const val TAG = "SenderTimeWork =>:"

class SendTimeWork(context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters){
    override fun doWork(): Result {
        var returnVal = Result.failure()
        val time = Calendar.getInstance().time
        val timeString = time.toString()
        val timeSubmissionApi = RetrofitClient.getInstance().create(TimeSubmissionAPI::class.java)
        val timeSubmissionCall = timeSubmissionApi.sendTime(timeString)
        timeSubmissionCall.enqueue(object:Callback<Data>{
            override fun onFailure(call: Call<Data>, t: Throwable) {
                returnVal = Result.failure()
                Log.d(TAG,"Sending Time failed..!")
            }

            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                returnVal = Result.success()
                Log.d(TAG,"Sending Time suscess..!")
            }

        })
        return returnVal
    }

}