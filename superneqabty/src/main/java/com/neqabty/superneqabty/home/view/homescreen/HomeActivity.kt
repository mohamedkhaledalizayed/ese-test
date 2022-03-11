package com.neqabty.superneqabty.home.view.homescreen

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
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
import com.valify.valify_ekyc.sdk.Valify
import com.valify.valify_ekyc.sdk.ValifyConfig
import com.valify.valify_ekyc.sdk.ValifyFactory
import com.valify.valify_ekyc.viewmodel.ValifyData
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.util.*


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding>(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private val homeViewModel: HomeViewModel by viewModels()
    private val mAdapter = NewsAdapter()
    private val listAds = ArrayList<AdEntity>()
    val list = mutableListOf<CarouselItem>()
    lateinit var valifyClient: Valify
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.home_title)
        toolbar = binding.contentActivity.toolbar.toolbar

        toolbar.overflowIcon = getDrawable(R.drawable.ic_baseline_more_vert_24)
        drawer = binding.drawerLayout
        val carousel: ImageCarousel = findViewById(R.id.carousel)
        carousel.registerLifecycle(lifecycle)

        binding.contentActivity.tvNewsAll.setOnClickListener {
            val intent = Intent(this@HomeActivity, NewsListActivity::class.java)
            intent.putExtra("id", sharedPreferences.mainSyndicate)
            startActivity(intent)
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
        homeViewModel.news.observe(this) {
            mAdapter.submitList(it)
        }

        homeViewModel.getAds()
        homeViewModel.ads.observe(this) {
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
                if (listAds[position].type == "internal") {
                    val intent = Intent(this@HomeActivity, NewsDetailsActivity::class.java)
                    intent.putExtra("id", listAds[position].newsId)
                    startActivity(intent)
                } else {
                    if (listAds[position].url.isEmpty()) {
                        return
                    }
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(listAds[position].url)
                    startActivity(intent)
                }
                Toast.makeText(this@HomeActivity, "${listAds[position].id}", Toast.LENGTH_LONG)
                    .show()
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
        valifyClient = ValifyFactory(applicationContext).client

        homeViewModel.token.observe(this) {
            val valifyBuilder = ValifyConfig.Builder()
            valifyBuilder.setBaseUrl("https://valifystage.com/")
            valifyBuilder.setAccessToken(it.data.accessToken)
            valifyBuilder.setBundleKey("13415658ea504635a05aaab8465e5005")
            valifyBuilder.setLanguage("ar")
            try {
                valifyClient.startActivityForResult(this,1,valifyBuilder.build())
            }catch (e:Throwable){

            }
        }
    }

    private fun logout(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("تنبيه")
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "موافق"
        ) { dialog, _ ->
            dialog.dismiss()
            sharedPreferences.name = ""
            sharedPreferences.mobile = ""
            onResume()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, "لا"
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    override fun onResume() {
        super.onResume()
        if (sharedPreferences.mobile.isNotEmpty()) {
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).visibility =
                View.VISIBLE
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).text =
                Html.fromHtml(getString(R.string.menu_memberName, sharedPreferences.name))
            binding.navView.getHeaderView(0)
                .findViewById<TextView>(R.id.tvMobileNumber).visibility = View.VISIBLE
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvMobileNumber).text =
                Html.fromHtml(getString(R.string.menu_mobileNumber, sharedPreferences.mobile))
        } else {
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).visibility =
                View.GONE
            binding.navView.getHeaderView(0)
                .findViewById<TextView>(R.id.tvMobileNumber).visibility = View.GONE
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
            R.id.valify -> {
                openVlaify()
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

    private fun openVlaify() {

        homeViewModel.getToken()

    }

    override fun onBackPressed() {
        showAlertDialog("هل تريد الخروج من التطبيق!")
    }

    private fun showAlertDialog(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("تنبيه")
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "موافق"
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, "لا"
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_syndicate) {
            val intent = Intent(this@HomeActivity, SyndicateActivity::class.java)
            startActivity(intent)
            finishAffinity()
        } else if (item.itemId == R.id.auth) {
            if (sharedPreferences.mobile.isNotEmpty()) {
                logout("هل تريد تسجيل خروج!")
                return true
            }
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            intent.putExtra("code", sharedPreferences.code)
            intent.putExtra(
                Intent.EXTRA_INTENT,
                Intent(this@HomeActivity, SignupActivity::class.java)
            )
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val item = menu.findItem(R.id.auth)
        if (sharedPreferences.mobile.isNotEmpty()) {
            item.title = getString(R.string.logout_title)
        }else{
            item.title = getString(R.string.login_title)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        valifyClient.handleActivityResult(resultCode, data, object : Valify.ValifyResultListener {
            override fun onSuccess(
                valifyToken: String, valifyData: ValifyData
            ) {
                Log.e("neqabty", "onSuccess")
            }

            override fun onExit(
                valifyToken: String, step: String,
                valifyData: ValifyData
            ) {
                Log.e("neqabty", "onExit")
            }

            override fun onError(
                valifyToken: String, errorCode: Int,
                step: String, valifyData: ValifyData
            ) {
                Log.e("neqabty", "onError")
            }
        })
    }
}