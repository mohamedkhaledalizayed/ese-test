package com.neqabty.healthcare.mega.home.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
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
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.signup.presentation.view.SignupActivity
import com.neqabty.healthcare.chefaa.verifyuser.view.VerifyUserActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityMainBinding
import com.neqabty.healthcare.commen.aboutapp.AboutAppActivity
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.complains.view.getcomplains.ComplainsActivity
import com.neqabty.healthcare.commen.contactus.ContactUsActivity
import com.neqabty.healthcare.mega.payment.view.selectservice.PaymentsActivity
import com.neqabty.healthcare.commen.profile.view.profile.ProfileActivity
import com.neqabty.healthcare.commen.settings.SettingsActivity
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.view.newsdetails.NewsDetailsActivity
import com.neqabty.healthcare.news.view.newslist.NewsListActivity
import com.neqabty.healthcare.commen.checkaccountstatus.view.CheckAccountActivity
import com.neqabty.healthcare.commen.clinido.view.ClinidoActivity
import com.neqabty.healthcare.commen.complains.view.addcomplain.AddComplainActivity
import com.neqabty.healthcare.commen.invoices.view.InvoicesActivity
import com.neqabty.healthcare.commen.pharmacy.PharmacyActivity
import com.neqabty.healthcare.core.data.Cart
import com.neqabty.healthcare.core.data.Constants.cart
import com.neqabty.healthcare.core.data.Constants.mobileNumber
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.SehaHomeActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import dmax.dialog.SpotsDialog
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.util.*


@AndroidEntryPoint
class MegaHomeActivity : BaseActivity<ActivityMainBinding>(),
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var loading: AlertDialog
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private val homeViewModel: HomeViewModel by viewModels()
    private val mAdapter = NewsAdapter()
    private var title = ""
    private val syndicatesAdapter = NewsAdapter()
    private val listAds = ArrayList<AdEntity>()
    private val list = mutableListOf<CarouselItem>()
    private var isGuest = false
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        isGuest = intent.getBooleanExtra("isGuest", false)
        init()
        setupToolbar(title = "${if (isGuest) intent.getStringExtra("syndicateName") else sharedPreferences.syndicateName}")
        toolbar = binding.toolbar

        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        toolbar.overflowIcon = getDrawable(R.drawable.ic_baseline_more_vert_24)
        drawer = binding.drawerLayout
        val carousel: ImageCarousel = findViewById(R.id.carousel)
        carousel.registerLifecycle(lifecycle)

        binding.tvNewsAll.setOnClickListener {
            val intent = Intent(this@MegaHomeActivity, NewsListActivity::class.java)
            intent.putExtra("id", sharedPreferences.code)
            intent.putExtra("type", Constants.GENERAL_NEWS)
            startActivity(intent)
        }

        binding.tvSyndicateNewsAll.setOnClickListener {
            val intent = Intent(this@MegaHomeActivity, NewsListActivity::class.java)
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
                            binding.allNewsContainer.visibility = View.GONE
                            binding.newsRecycler.visibility = View.GONE
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

        binding.newsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            NewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@MegaHomeActivity, NewsDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }
        //End of General News



        //Start of Syndicates News
        homeViewModel.getSyndicateNews(if (isGuest) intent.getStringExtra("code")!! else sharedPreferences.code)
        homeViewModel.syndicatesNews.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        loading.show()
                    }
                    Status.SUCCESS -> {
                        loading.dismiss()
                        if (resource.data!!.isEmpty()){
                            binding.syndicatesNewsContainer.visibility = View.GONE
                            binding.syndicateNewsRecycler.visibility = View.GONE
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

        binding.syndicateNewsRecycler.adapter = syndicatesAdapter
        syndicatesAdapter.onItemClickListener = object :
            NewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@MegaHomeActivity, NewsDetailsActivity::class.java)
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
        //End of Ads

        binding.navView.setNavigationItemSelectedListener(this)

        binding.ivSubscription.setOnClickListener {
            if (sharedPreferences.isAuthenticated && sharedPreferences.isSyndicateMember){
                if (!isGuest){
                    val intent = Intent(this@MegaHomeActivity, PaymentsActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@MegaHomeActivity, "هذه الخدمة متاحة لاعضاء النقابة فقط.", Toast.LENGTH_LONG).show()
                }
            }else{
                askForLogin(resources.getString(R.string.not_found))
            }
        }

        binding.visitHomeImage.setOnClickListener {
            Toast.makeText(this, getString(R.string.service_unavailable), Toast.LENGTH_LONG).show()
        }

        binding.pharmacyImage.setOnClickListener {
            val intent = Intent(this, PharmacyActivity::class.java)
            startActivity(intent)
        }

        binding.doctorImage.setOnClickListener {
            if (sharedPreferences.isAuthenticated) {
                homeViewModel.getUrl(
                    phone = sharedPreferences.mobile,
                    type = "doctors",
                    name = sharedPreferences.name,
                    entityCode = sharedPreferences.code
                )
                title = "حجز أطباء"
            } else {
                askForLogin("عفوا هذا الرقم غير مسجل من قبل، برجاء تسجيل الدخول.")
            }
        }


        homeViewModel.clinidoUrl.observe(this){
            when(it.status){
                Status.LOADING ->{
                    loading.show()
                }
                Status.SUCCESS ->{
                    loading.dismiss()
                    if (it.data!!.status){
                        val intent = Intent(this, ClinidoActivity::class.java)
                        intent.putExtra("url", it.data.url)
                        intent.putExtra("title", title)
                        startActivity(intent)
                    }else if (it.data.status_code == 405) {
                        mobileNumber = sharedPreferences.mobile
                        startActivity(Intent(this, VerifyUserActivity::class.java))
                        Toast.makeText(this, it.data.message, Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, it.data.message, Toast.LENGTH_LONG).show()
                    }
                }
                Status.ERROR ->{
                    loading.dismiss()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        //Start of logout
        homeViewModel.logoutStatus.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        loading.show()
                    }
                    Status.SUCCESS -> {
                        loading.dismiss()
                        if (resource.data!!){
                            sharedPreferences.mobile = ""
                            sharedPreferences.isPhoneVerified = false
                            sharedPreferences.isAuthenticated = false
                            sharedPreferences.isSyndicateMember = false
                            sharedPreferences.code = ""
                            sharedPreferences.token = ""
                            sharedPreferences.mainSyndicate = 0
                            sharedPreferences.image = ""
                            sharedPreferences.syndicateName = ""
                            sharedPreferences.membershipId = ""
                            drawer.close()
                            val intent = Intent(this, CheckAccountActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }
                        else{
                            Toast.makeText(this, "لقد حدث خطاء ما.", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        loading.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        //End of logout
    }

    @SuppressLint("CutPasteId")
    private fun init() {
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.syndicate_name).text =
            "${sharedPreferences.syndicateName}"
        if (!sharedPreferences.image.isNullOrEmpty()){
            Picasso.get().load(sharedPreferences.image).placeholder(R.drawable.logo).into(binding.navView.getHeaderView(0).findViewById<CircleImageView>(R.id.image))
        }else{
            Picasso.get().load(R.drawable.logo).into(binding.navView.getHeaderView(0).findViewById<CircleImageView>(R.id.image))
        }

        if (sharedPreferences.isAuthenticated && sharedPreferences.isSyndicateMember) {
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

            if (sharedPreferences.isAuthenticated) {
                val menu: Menu = binding.navView.menu

                val logout: MenuItem = menu.findItem(R.id.logout)
                logout.title = resources.getString(R.string.logout_title)
            }
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).visibility =
                View.GONE
            binding.navView.getHeaderView(0)
                .findViewById<TextView>(R.id.tvMobileNumber).visibility = View.GONE
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.profile -> {
                if (sharedPreferences.isAuthenticated && sharedPreferences.isSyndicateMember){
                    val intent = Intent(this@MegaHomeActivity, ProfileActivity::class.java)
                    startActivity(intent)
                }else{
                    askForLogin(resources.getString(R.string.not_found))
                }
            }
            R.id.seha -> {
                val intent = Intent(this@MegaHomeActivity, SehaHomeActivity::class.java)
                startActivity(intent)
            }
            R.id.packages -> {
                if (sharedPreferences.isAuthenticated && sharedPreferences.isSyndicateMember){
                    val intent = Intent(this@MegaHomeActivity, com.neqabty.healthcare.sustainablehealth.mypackages.presentation.ProfileActivity::class.java)
                    startActivity(intent)
                }else{
                    askForLogin(resources.getString(R.string.not_found))
                }
            }
            R.id.news -> {
                val intent = Intent(this@MegaHomeActivity, NewsListActivity::class.java)
                intent.putExtra("id", sharedPreferences.code)
                intent.putExtra("type", Constants.SYNDICATE_NEWS)
                startActivity(intent)
            }
            R.id.payment -> {
                if (sharedPreferences.isAuthenticated && sharedPreferences.isSyndicateMember){
                    if (!isGuest){
                        val intent = Intent(this@MegaHomeActivity, InvoicesActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@MegaHomeActivity, "هذه الخدمة متاحة لاعضاء النقابة فقط.", Toast.LENGTH_LONG).show()
                    }
                }else{
                    askForLogin(resources.getString(R.string.not_found))
                }
            }
            R.id.about_app_fragment -> {
                val intent = Intent(this@MegaHomeActivity, AboutAppActivity::class.java)
                startActivity(intent)
            }
            R.id.settings_fragment -> {
                val intent = Intent(this@MegaHomeActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.suggestions -> {
                startActivity(Intent(this, AddComplainActivity::class.java))
            }

            R.id.contactus_fragment -> {
                startActivity(Intent(this, ContactUsActivity::class.java))
            }
            R.id.logout -> {
                if (sharedPreferences.isAuthenticated) {
                    logout(getString(R.string.log_out))
                }else{
                    sharedPreferences.mobile = ""
                    sharedPreferences.email = ""
                    sharedPreferences.isPhoneVerified = false
                    sharedPreferences.isAuthenticated = false
                    sharedPreferences.isSyndicateMember = false
                    sharedPreferences.code = ""
                    sharedPreferences.token = ""
                    sharedPreferences.mainSyndicate = 0
                    sharedPreferences.image = ""
                    sharedPreferences.syndicateName = ""
                    sharedPreferences.membershipId = ""
                    val intent = Intent(this, CheckAccountActivity::class.java)
                    startActivity(intent)
                    finishAffinity()                    }
                }

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
//            homeViewModel.logout()
            sharedPreferences.mobile = ""
            sharedPreferences.email = ""
            sharedPreferences.isPhoneVerified = false
            sharedPreferences.isAuthenticated = false
            sharedPreferences.isSyndicateMember = false
            sharedPreferences.code = ""
            sharedPreferences.token = ""
            sharedPreferences.mainSyndicate = 0
            sharedPreferences.image = ""
            sharedPreferences.syndicateName = ""
            sharedPreferences.membershipId = ""
            cart = Cart()
            drawer.close()
            val intent = Intent(this, CheckAccountActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    override fun onBackPressed() {

        if (drawer.isOpen){
            drawer.closeDrawer(GravityCompat.START)
            return
        }

        if (sharedPreferences.isAuthenticated && sharedPreferences.isSyndicateMember){
            closeApp(getString(R.string.exit))
        }else{
            finish()
        }
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
            val intent = Intent(this@MegaHomeActivity, SignupActivity::class.java)
            startActivity(intent)
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

}