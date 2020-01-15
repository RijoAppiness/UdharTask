package com.example.udhartask.views.home_screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.udhartask.R
import com.example.udhartask.models.Article
import com.example.udhartask.views.web_view_screen.WebViewActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_news.*

const val NEWS_URL = "news_url"
class NewsViewActivity : AppCompatActivity() {

    private lateinit var newsViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        RV_news.layoutManager = LinearLayoutManager(this)
        val rv_adaptor = RVadaptor(this)
        RV_news.adapter =rv_adaptor

        newsViewModel.loadData()
        newsViewModel.observableErrorMessage.observe(this, Observer{
            Snackbar.make(root_layout,it,Snackbar.LENGTH_SHORT).show()
        })
        newsViewModel.observableNewsResponse.observe(this,Observer{
            rv_adaptor.submitList(it)
        })
    }
    class RVadaptor(val context:Context):ListAdapter<Article, RVadaptor.ViewHolder>(
        diffcallback
    ){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_content_design,parent,false)
            return ViewHolder(
                view
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.apply{

                Picasso.get().load(getItem(position).urlToImage).placeholder(R.drawable.error)
                    .error(R.drawable.error).into(poster)
                title.text = getItem(position).title
                //TVtitleAndRelease.isSelected = true
                description.text = getItem(position).description
                itemview.setOnClickListener {
                    val intent = Intent(context,
                        WebViewActivity::class.java)
                    intent.putExtra(NEWS_URL,getItem(position).url)
                    context.startActivity(intent)
                }

            }

        }


        class ViewHolder(view: View):RecyclerView.ViewHolder(view){
          val title = view.findViewById<TextView>(R.id.TV_title)
          val description = view.findViewById<TextView>(R.id.TV_description)
          val poster = view.findViewById<ImageView>(R.id.IV_poster)
          val itemview = view
        }

        companion object{
            val diffcallback = object:DiffUtil.ItemCallback<Article>(){
                override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                    return oldItem.title==newItem.title
                }

                override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                    return oldItem.title==newItem.title
                }

            }
        }
    }
}
