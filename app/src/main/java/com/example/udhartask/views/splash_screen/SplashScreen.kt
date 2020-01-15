package com.example.udhartask.views.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.udhartask.R
import com.example.udhartask.views.home_screen.NewsViewActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed(object:Runnable{
            override fun run() {
                startActivity(Intent(this@SplashScreen, NewsViewActivity::class.java))
                finish()
            }

        },2000)
    }
}
