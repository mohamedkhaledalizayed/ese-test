package com.neqabty.news.modules.home.presentation.view.homescreen

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.navigation.NavigationView
import com.neqabty.ads.modules.home.domain.entity.AdEntity
import com.neqabty.news.R
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.neqabty.news.modules.home.presentation.view.newsdetails.NewsDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private val homeViewModel: HomeViewModel by viewModels()
    private val mAdapter = NewsAdapter()
    private val imageList = ArrayList<SlideModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.toolbar)

        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp)
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
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@HomeActivity, NewsDetailsActivity::class.java)
                intent.putExtra("news", item)
                startActivity(intent)
                finish()
            }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.logout -> {

            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}