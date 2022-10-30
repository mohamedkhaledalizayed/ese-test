package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.auth.signup.presentation.view.SignupActivity
import com.neqabty.healthcare.databinding.ActivityHomeBinding
import com.neqabty.healthcare.sustainablehealth.contactus.ContactUsActivity
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.AdEntity
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.view.newsdetails.NewsDetailsActivity
import com.neqabty.healthcare.news.view.newslist.NewsListActivity
import com.neqabty.healthcare.sustainablehealth.checkaccountstatus.view.CheckAccountActivity
import com.neqabty.healthcare.sustainablehealth.settings.SettingsActivity
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.about.AboutFragment
import com.neqabty.healthcare.sustainablehealth.mypackages.presentation.ProfileActivity
import com.neqabty.healthcare.sustainablehealth.search.presentation.view.search.SearchActivity
import com.neqabty.healthcare.sustainablehealth.suggestions.presentation.SuggestionsActivity
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.util.ArrayList

@AndroidEntryPoint
class SehaHomeActivity : BaseActivity<ActivityHomeBinding>(), NavigationView.OnNavigationItemSelectedListener {

    private val mAdapter = OurNewsAdapter()
    private val aboutAdapter = AboutAdapter()
    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout
    private val listAds = ArrayList<AdEntity>()
    val list = mutableListOf<CarouselItem>()
    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(R.layout.activity_home)

        setContentView(binding.root)
        setupToolbar(titleResId = R.string.home_title)
        toolbar = binding.homeContent.customToolbar.toolbar

        drawer = binding.drawerLayout

        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.app_name,
            R.string.app_name
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        binding.navView.setNavigationItemSelectedListener(this)
        binding.homeContent.ourNewsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            OurNewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@SehaHomeActivity, NewsDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }

        //Get General News
        homeViewModel.getAllNews()
        homeViewModel.allNews.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
//                        loading.show()
                    }
                    Status.SUCCESS -> {
//                        loading.dismiss()
                        if (resource.data!!.isEmpty()){
                            binding.homeContent.newsContainer.visibility = View.GONE
                            binding.homeContent.ourNewsRecycler.visibility = View.GONE
                        }else{
                            mAdapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
//                        loading.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        binding.homeContent.aboutRecycler.adapter = aboutAdapter
        aboutAdapter.onItemClickListener = object :
            AboutAdapter.OnItemClickListener {
            override fun setOnItemClickListener(title: String, content: String) {
                aboutDetails(title, content)
            }
        }

        binding.homeContent.newsContainer.setOnClickListener {
            val intent = Intent(this@SehaHomeActivity, NewsListActivity::class.java)
            intent.putExtra("type", 1)
            startActivity(intent)
        }

        binding.homeContent.startNow.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        homeViewModel.getAboutList()
        homeViewModel.aboutList.observe(this){

            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        binding.homeContent.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS ->{
                        binding.homeContent.progressCircular.visibility = View.GONE
                        aboutAdapter.submitList(resource.data)
                    }
                    Status.ERROR ->{
                        binding.homeContent.progressCircular.visibility = View.GONE
                    }
                }
            }
        }
        val carousel: ImageCarousel = findViewById(R.id.carousel)
        carousel.registerLifecycle(lifecycle)

        //Start of Ads
        homeViewModel.getAds()
        homeViewModel.ads.observe(this) {
            it.let {resource ->

            when(resource.status){
                Status.LOADING ->{
                    binding.homeContent.progressCircularAds.visibility = View.VISIBLE
                }
                Status.SUCCESS ->{
                    binding.homeContent.progressCircularAds.visibility = View.GONE
                    if (resource.data!!.isNotEmpty()){
                        listAds.addAll(resource.data!!)
                        for (data: AdEntity in resource.data!!) {
                            list.add(
                                CarouselItem(
                                    imageUrl = data.image,
                                    caption = ""
                                )
                            )
                        }

                        carousel.setData(list)
                    }
                }
                Status.ERROR ->{
                    binding.homeContent.progressCircularAds.visibility = View.GONE
                }
            }
            }
        }
        //End of Ads
    }

    private fun init() {

        if (sharedPreferences.mobile.isNotEmpty()){
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.phone).text =
                "${sharedPreferences.mobile}"
        }else{
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.phone).text =
                ""
        }

        if (sharedPreferences.isAuthenticated){
            val menu: Menu = binding.navView.menu

            val logout: MenuItem = menu.findItem(R.id.logout)
            logout.title = resources.getString(R.string.logout_title)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.notification_menu, menu)
        return true
    }

    private fun aboutDetails(title: String, content: String) {
        val fm: FragmentManager = supportFragmentManager
        val dialog = AboutFragment.newInstance(title, content)
        dialog.show(fm, "")
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.profile -> {
                if (sharedPreferences.isAuthenticated){
                    val intent = Intent(this@SehaHomeActivity, com.neqabty.healthcare.sustainablehealth.profile.view.profile.ProfileActivity::class.java)
                    intent.putExtra("healthcare", true)
                    startActivity(intent)
                }else{
                    askForLogin(getString(R.string.not_found))
                }
            }
            R.id.packages -> {
                if (sharedPreferences.isAuthenticated){
                    val intent = Intent(this@SehaHomeActivity, ProfileActivity::class.java)
                    startActivity(intent)
                }else{
                    askForLogin(getString(R.string.not_found))
                }
            }
            R.id.wallet -> {
                if (sharedPreferences.isAuthenticated){
                    comingSoon(getString(R.string.comming))
//                    val intent = Intent(this@HomeActivity, WalletActivity::class.java)
//                    startActivity(intent)
                }else{
                    askForLogin(getString(R.string.not_found))
                }
            }
            R.id.settings -> {
                val intent = Intent(this@SehaHomeActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.contactus_fragment -> {
                val intent = Intent(this@SehaHomeActivity, ContactUsActivity::class.java)
                startActivity(intent)
            }
            R.id.suggestions -> {
                if (sharedPreferences.isAuthenticated){
                    val intent = Intent(this@SehaHomeActivity, SuggestionsActivity::class.java)
                    startActivity(intent)
                }else{
                    askForLogin(getString(R.string.not_found))
                }
            }
            R.id.logout -> {
                if (sharedPreferences.isAuthenticated) {
                    logout(getString(R.string.log_out))
                }else{
                    sharedPreferences.mobile = ""
                    sharedPreferences.isPhoneVerified = false
                    sharedPreferences.isAuthenticated = false
                    sharedPreferences.isSyndicateMember = false
                    sharedPreferences.code = ""
                    sharedPreferences.token = ""
                    sharedPreferences.mainSyndicate = 0
                    sharedPreferences.image = ""
                    sharedPreferences.syndicateName = ""
                    drawer.close()
                    val intent = Intent(this@SehaHomeActivity, CheckAccountActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            sharedPreferences.mobile = ""
            sharedPreferences.isPhoneVerified = false
            sharedPreferences.isAuthenticated = false
            sharedPreferences.isSyndicateMember = false
            sharedPreferences.code = ""
            sharedPreferences.token = ""
            sharedPreferences.mainSyndicate = 0
            sharedPreferences.image = ""
            sharedPreferences.syndicateName = ""
            drawer.close()
            val intent = Intent(this@SehaHomeActivity, CheckAccountActivity::class.java)
            startActivity(intent)
            finish()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    private fun askForLogin(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    private fun comingSoon(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    override fun onBackPressed() {
        closeApp(getString(R.string.exit))
    }

    private fun closeApp(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            finishAffinity()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }
}