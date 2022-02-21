package com.neqabty.superneqabty.home.view.homescreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.neqabty.ads.modules.home.domain.entity.AdEntity
import com.neqabty.login.modules.login.presentation.view.homescreen.LoginActivity
import com.neqabty.news.modules.home.presentation.view.newsdetails.NewsDetailsActivity
import com.neqabty.news.modules.home.presentation.view.newslist.NewsListActivity
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.aboutapp.AboutAppActivity
import com.neqabty.superneqabty.core.ui.BaseActivity
import com.neqabty.superneqabty.databinding.ActivityMainBinding
import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import com.neqabty.superneqabty.settings.SettingsActivity
import com.neqabty.superneqabty.syndicates.presentation.view.homescreen.SyndicateActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding>(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private val homeViewModel: HomeViewModel by viewModels()
    private val mAdapter = NewsAdapter()
    private val listAds = ArrayList<AdEntity>()
    val list = mutableListOf<CarouselItem>()

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.home_title)
        toolbar = findViewById<Toolbar>(R.id.toolbar)

        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val carousel: ImageCarousel = findViewById(R.id.carousel)
        carousel.registerLifecycle(lifecycle)

         if(sharedPreferences.mobile.isNotEmpty())
            findViewById<NavigationView>(R.id.nav_view).getHeaderView(0).findViewById<TextView>(R.id.bLogin).setText(R.string.logout_title)

        val r = object: Runnable, Serializable {
            override fun run() :  Unit {
                println("Hallo")
//                val intent = Intent(this@HomeActivity, SignupActivity::class.java)
//                startActivity(intent)
            }
        }
        findViewById<TextView>(R.id.tv_news_all).setOnClickListener {
            val intent = Intent(this@HomeActivity, NewsListActivity::class.java)
            intent.putExtra("id", 5)
            startActivity(intent)
        }

        findViewById<NavigationView>(R.id.nav_view).getHeaderView(0).findViewById<TextView>(R.id.bLogin).setOnClickListener {
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            intent.putExtra("listener", r)
            startActivity(intent)
        }

        findViewById<NavigationView>(R.id.nav_view).getHeaderView(0).findViewById<TextView>(R.id.bChangeSyndicate).setOnClickListener {
            val intent = Intent(this@HomeActivity, SyndicateActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.menu_ic)
        homeViewModel.getSyndicateNews(intent.getIntExtra("id", -1))
        homeViewModel.news.observe(this){
            mAdapter.submitList(it)
        }

        homeViewModel.getAds()
        homeViewModel.ads.observe(this){
            listAds.addAll(it)
                for (data: AdEntity in it) {
                    list.add(
                        CarouselItem(
                            imageUrl = data.image,
                            caption = data.title
                        )
                    )
                }

            carousel.setData(list)
        }

        carousel.carouselListener = object : CarouselListener {


            override fun onClick(position: Int, carouselItem: CarouselItem) {
                Toast.makeText(this@HomeActivity, "${listAds[position].id}", Toast.LENGTH_LONG).show()
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                // ...
            }

        }

        findViewById<RecyclerView>(R.id.news_recycler).adapter = mAdapter
        mAdapter.onItemClickListener = object :
            NewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@HomeActivity, NewsDetailsActivity::class.java)
                intent.putExtra("news", item)
                startActivity(intent)
            }
        }
        findViewById<NavigationView>(R.id.nav_view).setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.news -> {
                val intent = Intent(this@HomeActivity, NewsDetailsActivity::class.java)
                startActivity(intent)
            }
            R.id.payment -> {
                val intent = Intent(this@HomeActivity, NewsDetailsActivity::class.java)
                startActivity(intent)
            }
//            R.id.about_fragment -> {
//
//            }
            R.id.about_app_fragment -> {
                val intent = Intent(this@HomeActivity, AboutAppActivity::class.java)
                startActivity(intent)
            }
            R.id.settings_fragment -> {
                val intent = Intent(this@HomeActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.contactus_fragment -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:0235317300")
                startActivity(intent)
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}