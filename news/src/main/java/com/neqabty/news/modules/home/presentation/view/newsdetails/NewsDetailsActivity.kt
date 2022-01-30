package com.neqabty.news.modules.home.presentation.view.newsdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.news.R
import com.neqabty.news.modules.home.domain.entity.NewsEntity

class NewsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        val newsItem = intent.extras?.getParcelable<NewsEntity>("news")!!

        findViewById<TextView>(R.id.news_title).text = newsItem.headline
        findViewById<TextView>(R.id.content).text = newsItem.content
        findViewById<TextView>(R.id.news_date).text = newsItem.createdAt
    }
}