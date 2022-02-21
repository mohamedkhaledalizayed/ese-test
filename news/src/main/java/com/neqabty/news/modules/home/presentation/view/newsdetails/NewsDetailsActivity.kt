package com.neqabty.news.modules.home.presentation.view.newsdetails

import android.os.Bundle
import com.neqabty.news.core.ui.BaseActivity
import com.neqabty.news.databinding.ActivityNewsDetailsBinding
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.squareup.picasso.Picasso

class NewsDetailsActivity : BaseActivity<ActivityNewsDetailsBinding>() {

    override fun getViewBinding() = ActivityNewsDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val newsItem = intent.extras?.getParcelable<NewsEntity>("news")!!
        setupToolbar( title = newsItem.headline)
        Picasso.get().load(newsItem.image).into(binding.newsImage)
        binding.newsTitle.text = newsItem.headline
        binding.content.text = newsItem.content
        binding.newsDate.text = newsItem.createdAt
    }
}