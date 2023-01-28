package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen


import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.ActivitySehaHomeScreenBinding


class SehaHomeScreen : BaseActivity<ActivitySehaHomeScreenBinding>() {

    enum class MenuItems {
        SYNDICATE, SEHA, NEWS, MORE
    }

    override fun getViewBinding() = ActivitySehaHomeScreenBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadFragment(SyndicatesFragment())
        binding.syndicatesContainer.setOnClickListener { fu(MenuItems.SYNDICATE) }
        binding.sehaContainer.setOnClickListener { fu(MenuItems.SEHA) }
        binding.newsContainer.setOnClickListener { fu(MenuItems.NEWS) }
        binding.moreContainer.setOnClickListener { fu(MenuItems.MORE) }
    }

    private fun fu(menuItem: MenuItems){

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