package com.neqabty.news.modules.home.presentation.view.newsdetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.news.R
import com.neqabty.news.core.ui.BaseActivity
import com.neqabty.news.core.utils.Status
import com.neqabty.news.databinding.ActivityNewsDetailsBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsActivity : BaseActivity<ActivityNewsDetailsBinding>() {

    private val newsDetailsViewModel: NewsDetailsViewModel by viewModels()
    override fun getViewBinding() = ActivityNewsDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar( titleResId = R.string.news)
        val newsId = intent.getIntExtra("id", -1)

        newsDetailsViewModel.getNewsDetails(newsId)
        newsDetailsViewModel.newsDetails.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data != null){
                            Picasso.get().load(resource.data.image).into(binding.newsImage)
                            binding.newsTitle.text = resource.data.headline
                            binding.content.text = resource.data.content
                            binding.newsDate.text = resource.data.createdAt
                        }else{
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

    }
}