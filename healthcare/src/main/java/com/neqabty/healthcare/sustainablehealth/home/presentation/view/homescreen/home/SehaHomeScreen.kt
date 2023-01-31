package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.home


import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySehaHomeScreenBinding
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


@AndroidEntryPoint
class SehaHomeScreen : BaseActivity<ActivitySehaHomeScreenBinding>() {

    enum class MenuItems {
        SYNDICATE, SEHA, NEWS, MORE
    }

    private val homeViewModel: HomeViewModel by viewModels()
    val list = mutableListOf<CarouselItem>()
    override fun getViewBinding() = ActivitySehaHomeScreenBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadFragment(SyndicatesFragment())
        binding.syndicatesContainer.setOnClickListener { changeFragment(MenuItems.SYNDICATE) }
        binding.sehaContainer.setOnClickListener { changeFragment(MenuItems.SEHA) }
        binding.newsContainer.setOnClickListener { changeFragment(MenuItems.NEWS) }
        binding.moreContainer.setOnClickListener { changeFragment(MenuItems.MORE) }

        //Start of Ads
        homeViewModel.getAds()
        homeViewModel.ads.observe(this) {
            for (data: AdEntity in it) {
                list.add(
                    CarouselItem(
                        imageUrl = data.image,
                        caption = ""
                    )
                )
            }

            binding.carousel.setData(list)
        }
        //End of Ads

    }

    private fun changeFragment(menuItem: MenuItems){

        when(menuItem){
            MenuItems.SYNDICATE ->{
                loadFragment(SyndicatesFragment())
                binding.syndicateImage.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.tint))
                binding.syndicate.setTextColor(resources.getColor(R.color.tint))

                binding.sehaImage.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))
                binding.newsIame.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))
                binding.moreImage.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))

                binding.seha.setTextColor(resources.getColor(R.color.unselected_tint))
                binding.news.setTextColor(resources.getColor(R.color.unselected_tint))
                binding.more.setTextColor(resources.getColor(R.color.unselected_tint))
            }
            MenuItems.SEHA ->{
                loadFragment(SehaFragment())
                binding.sehaImage.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.tint))
                binding.seha.setTextColor(resources.getColor(R.color.tint))

                binding.syndicateImage.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))
                binding.newsIame.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))
                binding.moreImage.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))

                binding.syndicate.setTextColor(resources.getColor(R.color.unselected_tint))
                binding.news.setTextColor(resources.getColor(R.color.unselected_tint))
                binding.more.setTextColor(resources.getColor(R.color.unselected_tint))
            }
            MenuItems.NEWS ->{
                loadFragment(NewsFragment())
                binding.newsIame.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.tint))
                binding.news.setTextColor(resources.getColor(R.color.tint))

                binding.syndicateImage.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))
                binding.sehaImage.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))
                binding.moreImage.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))

                binding.syndicate.setTextColor(resources.getColor(R.color.unselected_tint))
                binding.seha.setTextColor(resources.getColor(R.color.unselected_tint))
                binding.more.setTextColor(resources.getColor(R.color.unselected_tint))
            }
            MenuItems.MORE ->{
                loadFragment(MoreFragment())
                binding.moreImage.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.tint))
                binding.more.setTextColor(resources.getColor(R.color.tint))

                binding.sehaImage.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))
                binding.newsIame.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))
                binding.newsIame.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.unselected_tint))

                binding.syndicate.setTextColor(resources.getColor(R.color.unselected_tint))
                binding.seha.setTextColor(resources.getColor(R.color.unselected_tint))
                binding.news.setTextColor(resources.getColor(R.color.unselected_tint))
            }
        }

    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

}