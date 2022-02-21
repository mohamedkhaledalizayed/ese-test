package com.neqabty.news.modules.home.presentation.view.newslist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.news.databinding.ActivityNewsListBinding
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.neqabty.news.modules.home.presentation.view.NewsAdapter
import com.neqabty.news.modules.home.presentation.view.NewsViewModel
import com.neqabty.news.modules.home.presentation.view.newsdetails.NewsDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListActivity : AppCompatActivity() {

    private val homeViewModel: NewsViewModel by viewModels()
    private lateinit var binding: ActivityNewsListBinding
    private val mAdapter = NewsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel.getSyndicateNews(intent.getIntExtra("id", -1))
        homeViewModel.news.observe(this){
            mAdapter.submitList(it)
        }

        binding.newsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            NewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@NewsListActivity, NewsDetailsActivity::class.java)
                intent.putExtra("news", item)
                startActivity(intent)
            }
        }

    }
}