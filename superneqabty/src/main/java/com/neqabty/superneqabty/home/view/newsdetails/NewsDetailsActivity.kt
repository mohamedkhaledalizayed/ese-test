package com.neqabty.superneqabty.home.view.newsdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

import com.neqabty.superneqabty.databinding.ActivityNewsDetailsBinding
import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import com.squareup.picasso.Picasso

class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailsBinding
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val newsItem = intent.extras?.getParcelable<NewsEntity>("news")!!

//        toolbar = binding.toolbar
//        setSupportActionBar(toolbar)
//        toolbar.title = newsItem.headline
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Picasso.get().load(newsItem.image).into(binding.newsImage)
        binding.newsTitle.text = newsItem.headline
        binding.content.text = newsItem.content
        binding.newsDate.text = newsItem.createdAt
    }
}