package com.neqabty.superneqabty.home.view.homescreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.neqabty.ads.modules.home.domain.entity.AdEntity
import com.neqabty.login.modules.login.presentation.view.homescreen.LoginActivity
import com.neqabty.news.modules.home.presentation.view.newsdetails.NewsDetailsActivity
import com.neqabty.news.modules.home.presentation.view.newslist.NewsListActivity
import com.neqabty.signup.modules.home.presentation.view.homescreen.SignupActivity
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.aboutapp.AboutAppActivity
import com.neqabty.superneqabty.core.ui.BaseActivity
import com.neqabty.superneqabty.core.utils.Constants
import com.neqabty.superneqabty.databinding.ActivityMainBinding
import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import com.neqabty.superneqabty.payment.PaymentsActivity
import com.neqabty.superneqabty.settings.SettingsActivity
import com.neqabty.superneqabty.syndicates.presentation.view.homescreen.SyndicateActivity
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

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
        toolbar = binding.contentActivity.toolbar.toolbar

        drawer = binding.drawerLayout
        val carousel: ImageCarousel = findViewById(R.id.carousel)
        carousel.registerLifecycle(lifecycle)

        binding.contentActivity.tvNewsAll.setOnClickListener {
            val intent = Intent(this@HomeActivity, NewsListActivity::class.java)
            intent.putExtra("id", sharedPreferences.mainSyndicate)
            startActivity(intent)
        }

        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.bLogin).setOnClickListener {
            if(sharedPreferences.mobile.isNotEmpty()){
                sharedPreferences.name = ""
                sharedPreferences.mobile = ""
                onResume()
                return@setOnClickListener
            }
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            intent.putExtra(Intent.EXTRA_INTENT, Intent(this@HomeActivity, SignupActivity::class.java))
            startActivity(intent)
        }

        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.bChangeSyndicate).setOnClickListener {
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
        homeViewModel.getSyndicateNews(sharedPreferences.mainSyndicate)
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
                            caption = ""
                        )
                    )
                }

            carousel.setData(list)
        }

        carousel.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                if (listAds[position].type == "internal"){
                    val intent = Intent(this@HomeActivity, NewsDetailsActivity::class.java)
                    intent.putExtra("id", listAds[position].newsId)
                    startActivity(intent)
                }else{
                    if (listAds[position].url.isEmpty()){
                        return
                    }
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(listAds[position].url)
                    startActivity(intent)
                }
                Toast.makeText(this@HomeActivity, "${listAds[position].id}", Toast.LENGTH_LONG).show()
            }
            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }
        }

        binding.contentActivity.newsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            NewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@HomeActivity, NewsDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }
        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
        if(sharedPreferences.mobile.isNotEmpty()){
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).visibility = View.VISIBLE
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).text = Html.fromHtml(getString(R.string.menu_memberName, sharedPreferences.name))
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvMobileNumber).visibility = View.VISIBLE
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvMobileNumber).text = Html.fromHtml(getString(R.string.menu_mobileNumber, sharedPreferences.mobile))
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.bLogin).setText(R.string.logout_title)
        }else{
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).visibility = View.GONE
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvMobileNumber).visibility = View.GONE
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.bLogin).setText(R.string.login_title)
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.news -> {
                val intent = Intent(this@HomeActivity, NewsListActivity::class.java)
                intent.putExtra("id", sharedPreferences.mainSyndicate)
                startActivity(intent)
            }
            R.id.payment -> {
                val intent = Intent(this@HomeActivity, PaymentsActivity::class.java)
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
                intent.data = Uri.parse("tel:${Constants.CALL_CENTER}")
                startActivity(intent)
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}