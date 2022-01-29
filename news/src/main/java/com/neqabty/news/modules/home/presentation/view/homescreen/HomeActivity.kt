package com.neqabty.news.modules.home.presentation.view.homescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.neqabty.ads.modules.home.domain.entity.AdEntity
import com.neqabty.news.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val mAdapter = NewsAdapter()
    private val imageList = ArrayList<SlideModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeViewModel.getSyndicateNews(intent.getIntExtra("id", -1))
        homeViewModel.news.observe(this){
            mAdapter.submitList(it)
        }

        homeViewModel.getAds()
        homeViewModel.ads.observe(this){

                for (data: AdEntity in it) {
                    imageList.add(
                        SlideModel(
                            data.image,
                            ScaleTypes.FIT
                        )
                    )
                }

                findViewById<ImageSlider>(R.id.image_slider).setImageList(imageList)
        }
        findViewById<RecyclerView>(R.id.news_recycler).adapter = mAdapter
        mAdapter.onItemClickListener = object :
            NewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {

            }
        }

    }
}