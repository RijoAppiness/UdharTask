package com.example.udhartask.views.web_view_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.udhartask.R
import com.example.udhartask.views.home_screen.NEWS_URL
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity() {
    private lateinit var newsUrl:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        newsUrl = intent.getStringExtra(NEWS_URL)
        WB_news.webViewClient = NEWS()
        WB_news.settings.javaScriptEnabled = true
        WB_news.loadUrl(newsUrl)
    }
    class NEWS: WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }
    }
}
