package com.neqabty.superneqabty.home.view.newsdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import com.squareup.picasso.Picasso

class NewsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        val newsItem = intent.extras?.getParcelable<NewsEntity>("news")!!

        Picasso.get().load(newsItem.image).into(findViewById<ImageView>(R.id.news_image))
        findViewById<TextView>(R.id.news_title).text = newsItem.headline
        findViewById<TextView>(R.id.content).text = newsItem.content
        findViewById<TextView>(R.id.news_date).text = newsItem.createdAt
    }
}