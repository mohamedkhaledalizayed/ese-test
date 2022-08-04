package com.neqabty.recruitment.modules.home.view



import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.core.utils.Status
import com.neqabty.recruitment.databinding.ActivityHomeBinding
import com.neqabty.recruitment.modules.home.domain.entity.ads.AdEntity
import com.neqabty.recruitment.modules.home.domain.entity.news.NewsEntity
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val mAdapter = NewsAdapter()
    private val listAds = ArrayList<AdEntity>()
    val list = mutableListOf<CarouselItem>()
    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.carousel.registerLifecycle(lifecycle)
        //Start of Ads
        homeViewModel.getAds()
        homeViewModel.ads.observe(this) {
            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircularAds.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircularAds.visibility = View.GONE
                        if (resource.data!!.isNotEmpty()) {
                            listAds.addAll(resource.data)
                            for (data: AdEntity in resource.data) {
                                list.add(
                                    CarouselItem(
                                        imageUrl = data.imageFile,
                                        caption = data.title
                                    )
                                )
                            }

                            binding.carousel.setData(list)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircularAds.visibility = View.GONE
                    }
                }
            }
        }
        //End of Ads



        //Get General News
        homeViewModel.getNews()
        homeViewModel.news.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        if (resource.data!!.isEmpty()){
                            binding.news.visibility = View.GONE
                            binding.newsRecycler.visibility = View.GONE
                        }else{
                            mAdapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        binding.newsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            NewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {

            }
        }

    }
}