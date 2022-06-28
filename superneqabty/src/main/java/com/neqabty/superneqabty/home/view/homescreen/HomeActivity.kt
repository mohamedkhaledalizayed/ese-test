package com.neqabty.superneqabty.home.view.homescreen

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
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
import com.neqabty.login.modules.login.presentation.view.homescreen.LoginActivity
import com.neqabty.news.modules.home.presentation.view.newsdetails.NewsDetailsActivity
import com.neqabty.news.modules.home.presentation.view.newslist.NewsListActivity
import com.neqabty.signup.modules.home.presentation.view.homescreen.SignupActivity
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.aboutapp.AboutAppActivity
import com.neqabty.superneqabty.core.ui.BaseActivity
import com.neqabty.superneqabty.core.utils.Constants
import com.neqabty.superneqabty.core.utils.Status
import com.neqabty.superneqabty.databinding.ActivityMainBinding
import com.neqabty.superneqabty.home.domain.entity.AdEntity
import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import com.neqabty.superneqabty.payment.PaymentsActivity
import com.neqabty.superneqabty.settings.SettingsActivity
import com.neqabty.superneqabty.syndicates.presentation.view.homescreen.SyndicateActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import dmax.dialog.SpotsDialog
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.util.*


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding>(),
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var loading: AlertDialog
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private val homeViewModel: HomeViewModel by viewModels()
    private val mAdapter = NewsAdapter()
    private val syndicatesAdapter = NewsAdapter()
    private val listAds = ArrayList<AdEntity>()
    val list = mutableListOf<CarouselItem>()
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.home_title)
        toolbar = binding.contentActivity.toolbar.toolbar

        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        toolbar.overflowIcon = getDrawable(R.drawable.ic_baseline_more_vert_24)
        drawer = binding.drawerLayout
        val carousel: ImageCarousel = findViewById(R.id.carousel)
        carousel.registerLifecycle(lifecycle)

        binding.contentActivity.tvNewsAll.setOnClickListener {
            val intent = Intent(this@HomeActivity, NewsListActivity::class.java)
            intent.putExtra("id", sharedPreferences.code)
            intent.putExtra("type", Constants.GENERAL_NEWS)
            startActivity(intent)
        }

        binding.contentActivity.tvSyndicateNewsAll.setOnClickListener {
            val intent = Intent(this@HomeActivity, NewsListActivity::class.java)
            intent.putExtra("id", sharedPreferences.code)
            intent.putExtra("type", Constants.SYNDICATE_NEWS)
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

        //Get General News
        homeViewModel.getAllNews()
        homeViewModel.allNews.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        loading.show()
                    }
                    Status.SUCCESS -> {
                        loading.dismiss()
                        if (resource.data!!.isEmpty()){
                            binding.contentActivity.allNewsContainer.visibility = View.GONE
                            binding.contentActivity.newsRecycler.visibility = View.GONE
                        }else{
                            mAdapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
                        loading.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
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
        //End of General News



        //Start of Syndicates News
        homeViewModel.getSyndicateNews("${sharedPreferences.code}")
        homeViewModel.syndicatesNews.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        loading.show()
                    }
                    Status.SUCCESS -> {
                        loading.dismiss()
                        if (resource.data!!.isEmpty()){
                            binding.contentActivity.syndicatesNewsContainer.visibility = View.GONE
                            binding.contentActivity.syndicateNewsRecycler.visibility = View.GONE
                        }else{
                            syndicatesAdapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
                        loading.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.contentActivity.syndicateNewsRecycler.adapter = syndicatesAdapter
        syndicatesAdapter.onItemClickListener = object :
            NewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@HomeActivity, NewsDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }
        //End of Syndicates News

        //Start of Ads
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
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }
        }
        //End of Ads

        binding.navView.setNavigationItemSelectedListener(this)

        binding.contentActivity.ivSubscription.setOnClickListener {
            val intent = Intent(this@HomeActivity, PaymentsActivity::class.java)
            startActivity(intent)
        }

        binding.contentActivity.healthcareImage.setOnClickListener {
            Toast.makeText(this, getString(R.string.service_unavailable), Toast.LENGTH_LONG).show()
        }

        binding.contentActivity.travelsImage.setOnClickListener {
            Toast.makeText(this, getString(R.string.service_unavailable), Toast.LENGTH_LONG).show()
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
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.syndicate_name).text =
            "${sharedPreferences.syndicateName}"
        if (!sharedPreferences.image.isNullOrEmpty()){
            Picasso.get().load(sharedPreferences.image).into(binding.navView.getHeaderView(0).findViewById<CircleImageView>(R.id.image))
        }

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
                intent.putExtra("id", sharedPreferences.code)
                intent.putExtra("type", Constants.SYNDICATE_NEWS)
                startActivity(intent)
            }
            R.id.payment -> {
                val intent = Intent(this@HomeActivity, PaymentsActivity::class.java)
                startActivity(intent)
            }
            R.id.about_app_fragment -> {
                val intent = Intent(this@HomeActivity, AboutAppActivity::class.java)
                startActivity(intent)
            }
            R.id.settings_fragment -> {
                val intent = Intent(this@HomeActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.contactus_fragment -> {

                try {
                    val emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.data = Uri.parse("mailto:")
                    emailIntent.type = "text/plain"
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("gts@neqabty.com"))
                    startActivity(emailIntent)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }

//                val intent = Intent(Intent.ACTION_DIAL)
//                intent.data = Uri.parse("tel:${Constants.CALL_CENTER}")
//                startActivity(intent)
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
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
}