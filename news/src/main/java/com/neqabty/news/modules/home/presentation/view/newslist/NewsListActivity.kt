package com.neqabty.news.modules.home.presentation.view.newslist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.news.core.ui.BaseActivity
import com.neqabty.news.core.utils.Status
import com.neqabty.news.databinding.ActivityNewsDetailsBinding
import com.neqabty.news.databinding.ActivityNewsListBinding
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.neqabty.news.modules.home.presentation.view.newsdetails.NewsDetailsActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListActivity : BaseActivity<ActivityNewsListBinding>() {

    private val homeViewModel: NewsViewModel by viewModels()
    private val mAdapter = NewsAdapter()
    override fun getViewBinding() = ActivityNewsListBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        if (intent.getIntExtra("id", -1) == -1 || intent.getIntExtra("type", -1) == 1){
            setupToolbar( title = "الاخبار")
            homeViewModel.getAllNews()
        }else{
            setupToolbar( title = "أخبار نقابتى")
            homeViewModel.getSyndicateNews(intent.getIntExtra("id", -1))
        }
        homeViewModel.news.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.isNotEmpty()){
                            mAdapter.submitList(resource.data)
                        }else{
                            binding.noNewsLayout.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        binding.newsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            NewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@NewsListActivity, NewsDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }

    }
}