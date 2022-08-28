package com.neqabty.meganeqabty.home.view.homescreen

import android.annotation.SuppressLint
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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.aboutapp.AboutAppActivity
import com.neqabty.meganeqabty.complains.view.ComplainsActivity
import com.neqabty.meganeqabty.core.utils.Constants
import com.neqabty.meganeqabty.databinding.ActivityMainBinding
import com.neqabty.meganeqabty.home.domain.entity.AdEntity
import com.neqabty.meganeqabty.payment.domain.entity.services.ServicesListEntity
import com.neqabty.meganeqabty.payment.view.selectservice.PaymentsActivity
import com.neqabty.meganeqabty.payment.view.selectservice.ServicesAdapter
import com.neqabty.meganeqabty.profile.view.profile.ProfileActivity
import com.neqabty.meganeqabty.settings.SettingsActivity
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.neqabty.news.modules.home.presentation.view.newsdetails.NewsDetailsActivity
import com.neqabty.news.modules.home.presentation.view.newslist.NewsListActivity
import com.neqabty.signup.modules.signup.presentation.view.homescreen.SignupActivity
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
    NavigationView.OnNavigationItemSelectedListener, IOnSendClickListener {
    private lateinit var loading: AlertDialog
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private val homeViewModel: HomeViewModel by viewModels()
    private val mAdapter = NewsAdapter()
    private val syndicatesAdapter = NewsAdapter()
    private val listAds = ArrayList<AdEntity>()
    private val list = mutableListOf<CarouselItem>()
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "${sharedPreferences.syndicateName}")
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
            R.string.app_name,
            R.string.app_name
        )


        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_menu)

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
                    if (listAds[position].newsId != null){
                        val intent = Intent(this@HomeActivity, NewsDetailsActivity::class.java)
                        intent.putExtra("id", listAds[position].newsId)
                        startActivity(intent)
                    }
                } else {
                    if (listAds[position].url.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(listAds[position].url)
                        startActivity(intent)
                    }
                }
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }
        }
        //End of Ads

        binding.navView.setNavigationItemSelectedListener(this)

        binding.contentActivity.ivSubscription.setOnClickListener {
            if (sharedPreferences.isAuthenticated){
                val intent = Intent(this@HomeActivity, PaymentsActivity::class.java)
                startActivity(intent)
            }else{
                askForLogin(resources.getString(R.string.not_found))
            }
        }

        binding.contentActivity.healthcareImage.setOnClickListener {
            Toast.makeText(this, getString(R.string.service_unavailable), Toast.LENGTH_LONG).show()
        }

        binding.contentActivity.travelsImage.setOnClickListener {
            Toast.makeText(this, getString(R.string.service_unavailable), Toast.LENGTH_LONG).show()
        }

        homeViewModel.complains.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        Toast.makeText(this, "تم إرسال طلبك بنجاح.", Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    @SuppressLint("CutPasteId")
    override fun onResume() {
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.syndicate_name).text =
            "${sharedPreferences.syndicateName}"
        if (!sharedPreferences.image.isNullOrEmpty()){
            Picasso.get().load(sharedPreferences.image).placeholder(R.drawable.logo).into(binding.navView.getHeaderView(0).findViewById<CircleImageView>(R.id.image))
        }else{
            Picasso.get().load(R.drawable.logo).into(binding.navView.getHeaderView(0).findViewById<CircleImageView>(R.id.image))
        }

        super.onResume()
        if (sharedPreferences.isAuthenticated) {
            val menu: Menu = binding.navView.menu

            val logout: MenuItem = menu.findItem(R.id.logout)
            logout.title = resources.getString(R.string.logout_title)

//            val syndicate: MenuItem = menu.findItem(R.id.syndicate)
//            syndicate.isVisible = false

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
            R.id.profile -> {
                if (sharedPreferences.isAuthenticated){
                    val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
                    startActivity(intent)
                }else{
                    askForLogin(resources.getString(R.string.not_found))
                }
            }
            R.id.news -> {
                val intent = Intent(this@HomeActivity, NewsListActivity::class.java)
                intent.putExtra("id", sharedPreferences.code)
                intent.putExtra("type", Constants.SYNDICATE_NEWS)
                startActivity(intent)
            }
            R.id.payment -> {
                if (sharedPreferences.isAuthenticated){
                    val intent = Intent(this@HomeActivity, PaymentsActivity::class.java)
                    startActivity(intent)
                }else{
                    askForLogin(resources.getString(R.string.not_found))
                }
            }
            R.id.about_app_fragment -> {
                val intent = Intent(this@HomeActivity, AboutAppActivity::class.java)
                startActivity(intent)
            }
            R.id.settings_fragment -> {
                val intent = Intent(this@HomeActivity, SettingsActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.suggestions -> {
                val fm: FragmentManager = supportFragmentManager
                if (sharedPreferences.isAuthenticated){
                    val dialog = SuggestionDialog.newInstance(sharedPreferences.mobile, sharedPreferences.email)
                    dialog.show(fm, "")
                    dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
                }else{
                    val dialog = SuggestionDialog()
                    dialog.show(fm, "")
                    dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
                }
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
                    finish()
                }
            }
//            R.id.syndicate -> {
//                    finish()
//            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.complain_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.suggestions){
            if (sharedPreferences.isAuthenticated) {
                val intent = Intent(this, ComplainsActivity::class.java)
                startActivity(intent)
            }else{
                askForLogin(resources.getString(R.string.not_found))
            }
        }
        return super.onOptionsItemSelected(item)
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
            finish()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    override fun onBackPressed() {
        closeApp(getString(R.string.close_app))
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

    private fun askForLogin(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(resources.getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.ok_btn)
        ) { dialog, _ ->
            dialog.dismiss()
            val intent = Intent(this@HomeActivity, SignupActivity::class.java)
            startActivity(intent)
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    override fun onClick(mobile: String, email: String, message: String) {
        homeViewModel.addComplain(mobile, email, message)
    }
}