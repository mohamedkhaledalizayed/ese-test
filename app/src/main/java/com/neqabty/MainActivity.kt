package com.neqabty

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.common.ExpandableListAdapter
import com.neqabty.presentation.entities.NavigationMenuItem
import com.neqabty.presentation.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPref: PreferencesHelper

    private val mainViewModel: MainViewModel by viewModels()

    lateinit var newToken: String

    var isAlertShown = false

    private val REQUEST_SMS = 0

    lateinit var mMenuAdapter: ExpandableListAdapter
    lateinit var listDataHeader: MutableList<NavigationMenuItem>
    lateinit var listDataChild: HashMap<NavigationMenuItem, List<NavigationMenuItem>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            customiseStatusbar()

        setTheme(when(sharedPref.fontSize) {
            "small" -> R.style.AppTheme_NoActionBar_SmallText
            "large" -> R.style.AppTheme_NoActionBar_LargeText
            else -> R.style.AppTheme_NoActionBar
        })

        setContentView(R.layout.main_activity)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LOCALE
        setSupportActionBar(toolbar)
        getAppConfig()
        checkRoot()
        startActivities()
        loadAds()

        Constants.isFirebaseTokenUpdated.observe(this, Observer {
            if (it.isNotBlank()){
                newToken = it
                if (!newToken.equals(sharedPref.token)) // Token has been changed
                {
                    if (sharedPref.user.isNotBlank()) { // verified
                        mainViewModel.login(sharedPref.mobile, sharedPref.user, newToken, sharedPref)
                    }
                }
            }
        })
        PushNotificationsWrapper().getToken(this)
    }

    private fun checkRoot() {
        if (DeviceUtils().isDeviceRooted()) {// || DeviceUtils().isEmulator()
            showAlertDialogAndExitApp(getString(R.string.root_msg));
        }
    }

    private fun startActivities() {
        val notificationId: String? = intent?.extras?.getString("notificationId")
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main)
        val navController = navController()
//        graph.desetDefaultArguments(intent.extras)

        if (notificationId != null)
            graph.startDestination = R.id.homeFragment
//        else if (!sharedPref.isIntroSkipped) // TODO
//            graph.startDestination = R.id.introFragment
        else if (sharedPref.isForceLogout == true){
            sharedPref.clearAll()
            graph.startDestination = R.id.loginFragment
        }
        else if (sharedPref.mobile.isEmpty()) // TODO
            graph.startDestination = R.id.loginFragment
        else
            graph.startDestination = R.id.homeFragment

        navHostFragment.navController.graph = graph
        supportFragmentManager.beginTransaction().setPrimaryNavigationFragment(navHostFragment)
                .commit()
        NavigationUI.setupActionBarWithNavController(this, navController, (drawer_layout as DrawerLayout))

        prepareNavigationMenuListData()
        mMenuAdapter = ExpandableListAdapter(this, listDataHeader, listDataChild)
        (nav_view as NavigationView).findViewById<ExpandableListView>(R.id.elvNavigationMenu).setAdapter(mMenuAdapter)
        (nav_view as NavigationView).setupWithNavController(navController)
        val appBarConfiguration =
                AppBarConfiguration(setOf(R.id.homeFragment, R.id.syndicatesFragment), (drawer_layout as DrawerLayout))
        toolbar.setupWithNavController(navController, appBarConfiguration)
        // ////////////////////////////////
        (drawer_layout as DrawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        (nav_view as NavigationView).findViewById<ExpandableListView>(R.id.elvNavigationMenu).setOnGroupClickListener { parent, v, groupPosition, id ->
            if(parent.expandableListAdapter.getChildrenCount(groupPosition) == 0) {
                listDataHeader[groupPosition].callback.invoke()
                (drawer_layout as DrawerLayout).closeDrawer(GravityCompat.START)
            }else{
                if(parent.isGroupExpanded(groupPosition))
                    parent.collapseGroup(groupPosition)
                else
                    parent.expandGroup(groupPosition)
            }
            true
        }

        (nav_view as NavigationView).findViewById<ExpandableListView>(R.id.elvNavigationMenu).setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            listDataChild[listDataHeader[groupPosition]]?.get(childPosition)?.callback?.invoke()
            (drawer_layout as DrawerLayout).closeDrawer(GravityCompat.START)
            true
        }

        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.homeFragment)
                supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(true)
                    setHomeAsUpIndicator(R.mipmap.menu_ic)
                    (drawer_layout as DrawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            else
                supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(true)
                    setHomeAsUpIndicator(R.drawable.ic_up)
                    (drawer_layout as DrawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
        })

        notificationId?.let {
            val args = Bundle()
            val serviceId: String? = intent?.extras?.getString("serviceId")!!

            args.putString("notificationId", it)
            args.putString("serviceId", serviceId)
            Navigation.findNavController(this, R.id.container)
                    .navigate(R.id.notificationDetailsFragment, args)
        }
    }

//region//

    private fun loadAds(){
        mainViewModel.getAds(Constants.AD_HOME)
    }
    private fun getAppConfig(){
        mainViewModel.viewState.observe(this, Observer {
            it.appConfigUI?.let {
                if(it.maintenanceStatus.status.toInt() != 0){
                    showAlertDialogAndExitApp(it.maintenanceStatus.statusMsg)
                    return@let
                }

                if (BuildConfig.VERSION_CODE < it.appVersion.toInt()) {
                    if (!isAlertShown)
                        showAlert()
                }
            }
        })
        mainViewModel.errorState.observe(this, Observer { error ->
            mainViewModel.getAppConfig()
        })
        mainViewModel.getAppConfig()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
                Navigation.findNavController(this, R.id.container),
                (drawer_layout as DrawerLayout)
        )
    }

    override fun onBackPressed() {
        if ((drawer_layout as DrawerLayout).isDrawerOpen(GravityCompat.START)) {
            (drawer_layout as DrawerLayout).closeDrawer(GravityCompat.START)
        } else {
            var currentFragment =
                    (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).childFragmentManager.fragments[0]
            if (currentFragment is OnBackPressedListener)
                currentFragment.onBackPressed()
            else
                super.onBackPressed()
        }
    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.update_msg))
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            val appPackageName = this.packageName
            try {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$appPackageName")
                        )
                )
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                )
            }
            showAlert()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
        isAlertShown = true
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun customiseStatusbar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setBackgroundDrawableResource(R.mipmap.full_bg)
    }

    override fun attachBaseContext(newBase: Context) {
        val context = Config.ContextWrapper.wrap(newBase, Locale(Config.LANGUAGE.toLowerCase()))
        super.attachBaseContext(context)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val notificationsItem = menu?.findItem(R.id.notifications_fragment)
        val favoritesItem = menu?.findItem(R.id.favorites_fragment)
        val searchItem = menu?.findItem(R.id.search_fragment)
//        val logoutItem = menu?.findItem(R.id.logout_fragment)

        var currentFragment =
                (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).childFragmentManager.fragments[0]
        notificationsItem?.isVisible = currentFragment is HasHomeOptionsMenu &&
                sharedPref.isRegistered // && sharedPref.notificationsCount != 0
//        logoutItem?.isVisible = currentFragment is HasHomeOptionsMenu &&
//                sharedPref.isRegistered
        ivHeaderLogo?.visibility = if (currentFragment is HasHomeOptionsMenu) View.VISIBLE else View.GONE
        favoritesItem?.isVisible = currentFragment is HasMedicalOptionsMenu // || currentFragment is HasFavoriteOptionsMenu
//        searchItem?.isVisible = currentFragment is HasMedicalOptionsMenu//TODO

//        if (currentFragment is HasFavoriteOptionsMenu)
//            favoritesItem?.setIcon(currentFragment.renderFav())

        var navigationView: NavigationView = (nav_view as NavigationView)
        var navMenu = navigationView.menu
//        navMenu.findItem(R.id.logout_fragment)?.isVisible = sharedPref.isRegistered
        navigationView.getHeaderView(0).findViewById<Button>(R.id.bLogout).setOnClickListener {
            sharedPref.isRegistered = false
            sharedPref.user = ""
            sharedPref.name = ""
            sharedPref.mobile = ""
            sharedPref.jwt = ""
            sharedPref.token = ""
            sharedPref.photo = ""
            sharedPref.notificationsCount = 0
            PushNotificationsWrapper().deleteToken(this)
            invalidateOptionsMenu()

            (drawer_layout as DrawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            Navigation.findNavController(this, R.id.container)
                    .navigate(R.id.openLoginFragment)
        }

        if (sharedPref.name.isNotEmpty()) {
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).visibility = View.VISIBLE
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).setText(Html.fromHtml(getString(R.string.menu_memberName, sharedPref.name)))

            //Profile Photo
            navigationView.getHeaderView(0).findViewById<ImageView>(R.id.ivPP).visibility = View.VISIBLE
            if(sharedPref.photo.isNotEmpty())
                navigationView.getHeaderView(0).findViewById<ImageView>(R.id.ivPP)
                .loadString(sharedPref.photo)
            else
                navigationView.getHeaderView(0).findViewById<ImageView>(R.id.ivPP).setImageResource(R.mipmap.profile_2_ic_2)

            navigationView.getHeaderView(0).findViewById<ImageView>(R.id.ivPP).setOnClickListener{
                Navigation.findNavController(this, R.id.container)
                    .navigate(R.id.profileFragment)
            }

            navigationView.getHeaderView(0).findViewById<Button>(R.id.bProfile).visibility = View.VISIBLE
            navigationView.getHeaderView(0).findViewById<Button>(R.id.bProfile).setOnClickListener{
                Navigation.findNavController(this, R.id.container)
                    .navigate(R.id.profileFragment)
            }
        } else {
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).visibility = View.GONE
            navigationView.getHeaderView(0).findViewById<ImageView>(R.id.ivPP).visibility = View.GONE
            navigationView.getHeaderView(0).findViewById<Button>(R.id.bProfile).visibility = View.GONE
        }
        if (sharedPref.user.isNotEmpty()) {
//            navigationView.getHeaderView(0).findViewById<TextView>(R.id.bChangePassword).visibility = View.VISIBLE
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberNumber).visibility = View.VISIBLE
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberNumber).setText(Html.fromHtml(getString(R.string.menu_syndicateNumber, sharedPref.user)))
        } else {
//            navigationView.getHeaderView(0).findViewById<TextView>(R.id.bChangePassword).visibility = View.INVISIBLE
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvMemberNumber).visibility = View.GONE
        }
        navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvMobileNumber).setText(Html.fromHtml(getString(R.string.menu_phoneNumber, sharedPref.mobile)))

        navigationView.getHeaderView(0).visibility = if (sharedPref.mobile.isNotEmpty()) View.VISIBLE else View.GONE

        if (!sharedPref.mobile.isNotEmpty())
            navigationView.getChildAt(0).setPadding(0, 100, 0, 0)
        else
            navigationView.getChildAt(0).setPadding(0, 0, 0, 0)

        val tvBadge = notificationsItem?.actionView?.findViewById<TextView>(R.id.tvBadge)
        if (sharedPref.notificationsCount == 0) tvBadge?.visibility = View.INVISIBLE
        else {
            tvBadge?.visibility = View.VISIBLE
            tvBadge?.setText(sharedPref.notificationsCount.toString())
        }
        val ivNotification = notificationsItem?.actionView?.findViewById<ImageView>(R.id.ivNotification)
        ivNotification?.setOnClickListener {
            Navigation.findNavController(this, R.id.container)
                    .navigate(R.id.notificationsFragment)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var currentFragment =
                (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).childFragmentManager.fragments[0]
        when (item?.itemId) {
            R.id.notifications_fragment -> {
                Navigation.findNavController(this, R.id.container)
                        .navigate(R.id.notificationsFragment)
            }
            R.id.search_fragment -> {
                Navigation.findNavController(this, R.id.container).navigate(R.id.searchFragment)
            }
            R.id.favorites_fragment -> {
                if (currentFragment is HasMedicalOptionsMenu)
                    Navigation.findNavController(
                            this,
                            R.id.container
                    ).navigate(R.id.favoritesFragment)
//                else if (currentFragment is HasFavoriteOptionsMenu)
//                    currentFragment.toggleFav()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showAlertDialogAndExitApp(message: String) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert_title))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_btn)
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }

        isAlertShown = true
        alertDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10000) {
                var currentFragment =
                        (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).childFragmentManager.fragments[0]
                currentFragment.onActivityResult(requestCode, resultCode, data)
//                paymentGateway.handle3DSecureAuthenticationResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onSaveInstanceState(@NonNull outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
    }

    private fun prepareNavigationMenuListData() {
        listDataHeader = mutableListOf<NavigationMenuItem>()
        listDataChild = HashMap<NavigationMenuItem, List<NavigationMenuItem>>()

        val userServicesItem = NavigationMenuItem(R.drawable.ic_menu_complaints, R.string.user_services_title, {

        })
        listDataHeader.add(userServicesItem)

        val tripsItem = NavigationMenuItem(R.drawable.ic_menu_trips, R.string.trips_title, {
            navController().navigate(R.id.tripsFragment)
        })
//        listDataHeader.add(tripsItem)
        val medicalServicesItem = NavigationMenuItem(R.drawable.ic_menu_medical, R.string.medical_services_title, {

        })
        listDataHeader.add(medicalServicesItem)

        val newsItem = NavigationMenuItem(R.drawable.ic_menu_news, R.string.news_title, {
            navController().navigate(R.id.newsFragment)
        })
        listDataHeader.add(newsItem)

        val paymentsItem = NavigationMenuItem(R.drawable.ic_menu_payments, R.string.inquiry_title, {
            navController().navigate(R.id.inquiryFragment)
        })
        listDataHeader.add(paymentsItem)

        val committeesItem = NavigationMenuItem(R.drawable.ic_menu_records, R.string.committees_title, {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.committeesFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.COMMITTEES)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })

        val engineeringRecordsItem = NavigationMenuItem(R.drawable.ic_menu_records, R.string.engineering_records_title, {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.engineeringRecordsDetailsFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.RECORDS)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })
//        listDataHeader.add(engineeringRecordsItem)

        val updateDataItem = NavigationMenuItem(R.drawable.ic_menu_update_data, R.string.update_data_title, {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.updateDataVerificationFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.UPDATE_DATA)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })
//        listDataHeader.add(updateDataItem)

        val complaintsItem = NavigationMenuItem(R.drawable.ic_menu_complaints, R.string.complaints_title, {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.complaintsFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.COMPLAINTS)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })
        listDataHeader.add(complaintsItem)

        val aboutItem = NavigationMenuItem(R.drawable.ic_menu_about_us, R.string.about_title, {
            navController().navigate(R.id.aboutFragment)
        })
        listDataHeader.add(aboutItem)

        val aboutappItem = NavigationMenuItem(R.drawable.ic_menu_about_app, R.string.aboutapp_title, {
            navController().navigate(R.id.aboutAppFragment)
        })
        listDataHeader.add(aboutappItem)

        val settingsItem = NavigationMenuItem(R.drawable.ic_menu_settings, R.string.settings_title,  {
            navController().navigate(R.id.settingsFragment)
        })
        listDataHeader.add(settingsItem)

        val contactusItem = NavigationMenuItem(R.drawable.ic_menu_contact_us, R.string.contactus_title, {
            (nav_view as NavigationView).getHeaderView(0).findViewById<Button>(R.id.tvMobileNumber).call(Constants.CALL_CENTER, this)
        })
        listDataHeader.add(contactusItem)

        // Adding userServicesList child data
        val userServicesList: MutableList<NavigationMenuItem> = mutableListOf()

        val userServiceTrackShipment = NavigationMenuItem(R.drawable.ic_menu_about_app, R.string.track_shipment_title,  {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.trackShipmentFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.TRACK_SHIPMENT)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })
//        userServicesList.add(userServiceTrackShipment)

        val userServiceChangeUserMobile = NavigationMenuItem(R.drawable.ic_menu_about_app, R.string.change_user_mobile_title, {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.changeUserMobileFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.CHANGE_USER_MOBILE)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })
        userServicesList.add(userServiceChangeUserMobile)

        val userServiceChangePassword = NavigationMenuItem(R.drawable.ic_menu_about_app, R.string.change_password_title, {
            if (sharedPref.isRegistered) {
                val bundle: Bundle = Bundle()
                bundle.putBoolean("isSetNew", false)
                bundle.putString("mobile", sharedPref.mobile)
                bundle.putString("otp", "")
                navController().navigate(R.id.openChangePasswordFragment, bundle)
            }
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.CHANGE_PASSWORD)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })
        userServicesList.add(userServiceChangePassword)

        listDataChild[userServicesItem] = userServicesList // Header, Child data



        // Adding medicalServicesList child data
        val medicalServicesList: MutableList<NavigationMenuItem> = mutableListOf()

        val medicalRenewItem = NavigationMenuItem(R.drawable.ic_menu_claiming, R.string.medical_renew_title, {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.medicalRenewFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.MEDICAL_RENEW)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })
        medicalServicesList.add(medicalRenewItem)

        val medicalItem = NavigationMenuItem(R.drawable.ic_menu_medical, R.string.medical_title, {
            navController().navigate(R.id.chooseAreaFragment)
        })
        medicalServicesList.add(medicalItem)

        val claimingItem = NavigationMenuItem(R.drawable.ic_menu_claiming, R.string.claiming_title, {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.claimingFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.CLAIMING)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })
        medicalServicesList.add(claimingItem)

        val medicalLettersItem = NavigationMenuItem(R.drawable.ic_menu_update_data, R.string.medical_letters_title, {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.medicalLettersFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.MEDICAL_LETTERS)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })
        medicalServicesList.add(medicalLettersItem)

        val medicalLettersInquiryItem = NavigationMenuItem(R.drawable.ic_menu_help, R.string.medical_letters_inquiry_title, {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.medicalLettersInquiryFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.MEDICAL_LETTERS_INQUIRY)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })
        medicalServicesList.add(medicalLettersInquiryItem)

        val pharmacyItem = NavigationMenuItem(R.drawable.ic_pharmacy_green, R.string.online_pharmacy_title, {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.onlinePharmacyFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.ONLINE_PHARMACY)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })
        medicalServicesList.add(pharmacyItem)

        val doctorsReservationItem = NavigationMenuItem(R.drawable.ic_menu_medical, R.string.doctors_reservation_title, {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.doctorsReservationFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.DOCTORS_RESERVATION)
                navController().navigate(R.id.signupFragment, bundle)
            }
        })

        listDataChild[medicalServicesItem] = medicalServicesList // Header, Child data

        mainViewModel.viewState.observe(this, Observer {
            it.appConfigUI?.let {
                if(it.vezeetaConfig.status)
                    if(!medicalServicesList.contains(doctorsReservationItem)) medicalServicesList.add(doctorsReservationItem)
                if(it.committeesStatus) {
                    if (!listDataHeader.contains(committeesItem)) listDataHeader.add(4, committeesItem)
                    mMenuAdapter.notifyDataSetChanged()
                }
            }
        })
    }
//endregion//
    fun navController() = Navigation.findNavController(this, R.id.container)
}
