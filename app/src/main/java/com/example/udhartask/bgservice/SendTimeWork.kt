package com.example.udhartask.bgservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.udhartask.R
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

class SendTimeWork(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
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
        var notificationChannel: NotificationChannel?
        var notification: Notification?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                "notification1",
                "notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)


        } else {

        }
        notification =
            NotificationCompat.Builder(context, "notification1").setContentTitle("Notification")
                .setContentText("This is a notification")
                .setSmallIcon(R.mipmap.ic_news_icon).build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(100, notification)

        return returnVal
    }

}