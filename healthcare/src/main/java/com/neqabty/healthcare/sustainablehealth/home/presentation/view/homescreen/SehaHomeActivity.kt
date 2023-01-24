package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen



import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.navigation.NavigationView
import com.neqabty.chefaa.modules.home.presentation.homescreen.ChefaaHomeActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.signup.presentation.view.SignupActivity
import com.neqabty.healthcare.commen.checkaccountstatus.view.CheckAccountActivity
import com.neqabty.healthcare.commen.contactus.ContactUsActivity
import com.neqabty.healthcare.commen.settings.SettingsActivity
import com.neqabty.healthcare.commen.syndicates.presentation.view.homescreen.SyndicateActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityHomeBinding
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.about.AboutFragment
import com.neqabty.healthcare.sustainablehealth.mypackages.presentation.ProfileActivity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.packages.PackagesEntity
import com.neqabty.healthcare.sustainablehealth.search.presentation.view.filter.FiltersViewModel
import com.neqabty.healthcare.sustainablehealth.search.presentation.view.searchresult.SearchResultActivity
import com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.view.SubscriptionActivity
import com.neqabty.healthcare.sustainablehealth.suggestions.presentation.SuggestionsActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import me.relex.circleindicator.CircleIndicator
import kotlin.math.abs


@AndroidEntryPoint
class SehaHomeActivity : BaseActivity<ActivityHomeBinding>(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var loading: AlertDialog
    private val aboutAdapter = AboutAdapter()
    private lateinit var toolbar: Toolbar
    lateinit var indicator: CircleIndicator
    private lateinit var drawer: DrawerLayout
    private val filtersViewModel: FiltersViewModel by viewModels()
    private val mAdapter = PackagesAdapter()
    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var customLinearLayoutManager: CustomLinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        setContentView(R.layout.activity_home)
        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.home_title)
        toolbar = binding.toolbar

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


        binding.aboutRecycler.adapter = aboutAdapter
        aboutAdapter.onItemClickListener = object :
            AboutAdapter.OnItemClickListener {
            override fun setOnItemClickListener(title: String, content: String) {
                aboutDetails(title, content,"")
            }
        }


        homeViewModel.getAboutList()
        homeViewModel.aboutList.observe(this){

            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS ->{
                        binding.progressCircular.visibility = View.GONE
                        aboutAdapter.submitList(resource.data)
                    }
                    Status.ERROR ->{
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        }
        indicator = findViewById(R.id.indicator)


        binding.packagesRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            PackagesAdapter.OnItemClickListener {
            override fun setOnRegisterClickListener(item: PackagesEntity) {

                if (item.serviceActionCode == null){
                    Toast.makeText(this@SehaHomeActivity, "حدث خطا", Toast.LENGTH_LONG).show()
                    return
                }
                if (sharedPreferences.isAuthenticated){
                    val intent = Intent(this@SehaHomeActivity, SubscriptionActivity::class.java)
                    intent.putExtra("name", item.name )
                    intent.putExtra("price", item.price )
                    intent.putExtra("vat", item.vat )
                    intent.putExtra("total", item.total )
                    intent.putExtra("serviceCode", item.serviceCode )
                    intent.putExtra("maxFollowers", item.maxFollower )
                    intent.putExtra("serviceActionCode", item.serviceActionCode )
                    intent.putExtra("subscriptionMode", true )
                    startActivity(intent)
                }else{
                    askForLogin("عفوا هذا الرقم غير مسجل من قبل، برجاء تسجيل الدخول.")
                }
            }

            override fun setOnMoreClickListener(title: String, content: String, code: String) {
                aboutDetails(title, content, code)
            }
        }

        if (sharedPreferences.code.isNullOrEmpty()){
            filtersViewModel.getPackages(Constants.NEQABTY_CODE)
        }else{
            filtersViewModel.getPackages(sharedPreferences.code)
        }
        filtersViewModel.packages.observe(this) {
            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        mAdapter.submitList(resource.data?.toMutableList())
                        indicator.createIndicators(mAdapter.itemCount, 0)
                        val position = (abs(mAdapter.itemCount * 0.5)).toInt()
                        centerAtPosition(position)
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }

            }
        }

        binding.search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(binding.search.text.toString().isNotEmpty()){
                        startActivity(Intent(this@SehaHomeActivity, SearchResultActivity::class.java)
                            .putExtra("name", binding.search.text.toString()))
                        return true
                    }else{
                        Toast.makeText(this@SehaHomeActivity, "من فضلك ادخل كلمة البحث", Toast.LENGTH_LONG).show()
                    }
                }
                return false
            }
        })


        binding.search.customSelectionActionModeCallback = actionMode

        binding.cvChefaa.setOnClickListener {

            if (sharedPreferences.isAuthenticated){
                val intent = Intent(this, ChefaaHomeActivity::class.java)
                intent.putExtra("user_number", sharedPreferences.mobile)
                intent.putExtra("mobile_number", sharedPreferences.mobile)
                intent.putExtra("country_code", sharedPreferences.mobile.substring(0,2))
                intent.putExtra("national_id", sharedPreferences.nationalId)
                intent.putExtra("name", sharedPreferences.name)
                intent.putExtra("jwt", "")
                startActivity(intent)
            }else{
                askForLogin("عفوا هذا الرقم غير مسجل من قبل، برجاء تسجيل الدخول.")
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
                            drawer.close()
                            val intent = Intent(this@SehaHomeActivity, CheckAccountActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }else{
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

        customLinearLayoutManager = CustomLinearLayoutManager(this)
        customLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.packagesRecycler.layoutManager = customLinearLayoutManager

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.packagesRecycler)

        binding.packagesRecycler.addItemDecoration(OffsetItemDecoration())

        var currentPosition = -1
        binding.packagesRecycler.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var position: Int = getCurrentItem()

                    if (currentPosition == 0){
                        indicator.animatePageSelected(0)
                    }else{
                        indicator.animatePageSelected(position.plus(1))
                    }

                    currentPosition = position
                }
            }
        })

    }

    private fun getCurrentItem(): Int {
        return (binding.packagesRecycler.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
    }

    private fun centerAtPosition(position: Int) {
        binding.packagesRecycler.post {
            val cardWidth = CardShapeView.factorWidth * binding.packagesRecycler.width
            val centerRecyclerViewPoint = binding.packagesRecycler.width * 0.5
            val center = centerRecyclerViewPoint - (cardWidth * 0.5)
            customLinearLayoutManager.scrollToPositionWithOffset(position, center.toInt())
        }
        indicator.animatePageSelected(2)
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

    private fun aboutDetails(title: String, content: String, code: String) {
        val fm: FragmentManager = supportFragmentManager
        val dialog = AboutFragment.newInstance(title, content, code)
        dialog.show(fm, "")
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.profile -> {
                if (sharedPreferences.isAuthenticated){
                    val intent = Intent(this@SehaHomeActivity, com.neqabty.healthcare.commen.profile.view.profile.ProfileActivity::class.java)
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
            R.id.medical_directory -> {
                val intent = Intent(this@SehaHomeActivity, SearchResultActivity::class.java)
                startActivity(intent)
            }
            R.id.wallet -> {
                if (sharedPreferences.isAuthenticated){
                    comingSoon(getString(R.string.comming))
                }else{
                    askForLogin(getString(R.string.not_found))
                }
            }
            R.id.syndicate -> {
                val intent = Intent(this@SehaHomeActivity, SyndicateActivity::class.java)
                startActivity(intent)
            }
            R.id.settings -> {
                val intent = Intent(this@SehaHomeActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.contactus_fragment -> {
                val intent = Intent(this@SehaHomeActivity, ContactUsActivity::class.java)
                intent.putExtra("key", 101)
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
                    finishAffinity()
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
//            homeViewModel.logout()
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
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            Constants.isSyndicateMember = false
            Constants.selectedSyndicateCode = ""
            Constants.selectedSyndicatePosition = 0
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
        if (sharedPreferences.isAuthenticated && !sharedPreferences.isSyndicateMember){
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
}