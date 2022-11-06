package com.neqabty.healthcare.commen.landing.view


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.checkaccountstatus.view.CheckAccountActivity
import com.neqabty.healthcare.commen.syndicates.presentation.view.homescreen.SyndicateActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityLandingPageBinding
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.view.newsdetails.NewsDetailsActivity
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.SehaHomeActivity
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@AndroidEntryPoint
class LandingPageActivity : BaseActivity<ActivityLandingPageBinding>() {

    private val mainAdapter = SyndicatesAdapter()
    private val mAdapter = OurNewsAdapter()
    private val landingViewModel: LandingViewModel by viewModels()
    val list = mutableListOf<CarouselItem>()
    override fun getViewBinding() = ActivityLandingPageBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(binding.root)

        binding.phone.text = "الرقم المسجل في الدخول : ${sharedPreferences.mobile}"
        val carousel: ImageCarousel = findViewById(R.id.carousel)
        carousel.registerLifecycle(lifecycle)

        //Start of Ads
        landingViewModel.getAds()
        landingViewModel.ads.observe(this) {
            for (data: AdEntity in it) {
                list.add(
                    CarouselItem(
                        imageUrl = data.image,
                        caption = ""
                    )
                )
            }

            carousel.setData(list)
        }
        //End of Ads

        landingViewModel.getSyndicates()
        landingViewModel.syndicates.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.isNotEmpty()){
                            binding.syndicatesRecycler.adapter = mainAdapter
                            mainAdapter.submitList(resource.data)
                        }else{
                            Toast.makeText(this, getString(R.string.no_syndicates), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        binding.syndicateBtn.setOnClickListener {
            startActivity(Intent(this, SyndicateActivity::class.java))
        }

        binding.neqabtyBtn.setOnClickListener {
            startActivity(Intent(this, SehaHomeActivity::class.java))
        }

        binding.changePhoneLayout.setOnClickListener {
            startActivity(Intent(this, CheckAccountActivity::class.java))
            finish()
        }

        binding.ourNewsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            OurNewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@LandingPageActivity, NewsDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }
        landingViewModel.getAllNews()
        landingViewModel.allNews.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.isEmpty()){
                            binding.newsContainer.visibility = View.INVISIBLE
                            binding.ourNewsRecycler.visibility = View.GONE
                        }else{
                            mAdapter.submitList(resource.data)
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