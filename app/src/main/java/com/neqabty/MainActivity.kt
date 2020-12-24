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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.util.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.main_activity.*
import java.io.IOException
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var mainViewModel: MainViewModel

    lateinit var newToken: String

    var isAlertShown = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            customiseStatusbar()
        setContentView(R.layout.main_activity)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        mainViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainViewModel::class.java)

        setSupportActionBar(toolbar)
        getMinSupportedVersion()
        getToken()
        setJWT()
        checkRoot()
        startActivities()

        Constants.isFirebaseTokenUpdated.observe(this, Observer {
            if (it)
                getToken()
        })
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    private fun checkRoot() {
        if (DeviceUtils().isDeviceRooted()) {
            showAlertDialogAndExitApp(getString(R.string.root_msg));
        }
    }

    private fun startActivities() {
        val notificationId: String? = intent?.extras?.getString("notificationId")
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main)
        val navController = Navigation.findNavController(this, R.id.container)
//        graph.desetDefaultArguments(intent.extras)

        if (notificationId != null)
            graph.startDestination = R.id.homeFragment
//        else if (!PreferencesHelper(this).isIntroSkipped) // TODO
//            graph.startDestination = R.id.introFragment
        else if (PreferencesHelper(this).mobile.isEmpty()) // TODO
            graph.startDestination = R.id.loginFragment
        else
            graph.startDestination = R.id.homeFragment

        navHostFragment.navController.graph = graph
        supportFragmentManager.beginTransaction().setPrimaryNavigationFragment(navHostFragment)
                .commit()
        NavigationUI.setupActionBarWithNavController(this, navController, (drawer_layout as DrawerLayout))
        (nav_view as NavigationView).setupWithNavController(navController)
        val appBarConfiguration =
                AppBarConfiguration(setOf(R.id.homeFragment, R.id.syndicatesFragment), (drawer_layout as DrawerLayout))
        toolbar.setupWithNavController(navController, appBarConfiguration)
        // ////////////////////////////////
        (drawer_layout as DrawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        (nav_view as NavigationView).itemIconTintList = null
        (nav_view as NavigationView).setNavigationItemSelectedListener {
            (drawer_layout as DrawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            when (it.itemId) {
                R.id.home_fragment -> {
                    (drawer_layout as DrawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                R.id.news_fragment -> {
                    navController.navigate(R.id.newsFragment)
                }
//                R.id.trips_fragment -> {
//                    navController.navigate(R.id.tripsFragment)
//                }
                R.id.medical_renew_fragment -> { // TODO
                    if (PreferencesHelper(this).isRegistered)
                        navController.navigate(R.id.medicalRenewFragment)
                    else {
                        val bundle: Bundle = Bundle()
                        bundle.putInt("type", Constants.MEDICAL_RENEW)
                        navController.navigate(R.id.mobileFragment, bundle)
                    }
                }
                R.id.claiming_fragment -> { // TODO
                    Toast.makeText(this, getString(R.string.closed_claiming), Toast.LENGTH_SHORT).show()

//                    if (PreferencesHelper(this).isRegistered)
//                        navController.navigate(R.id.claimingFragment)
//                    else {
//                        val bundle: Bundle = Bundle()
//                        bundle.putInt("type", Constants.CLAIMING)
//                        navController.navigate(R.id.mobileFragment, bundle)
//                    }
                }
                R.id.medical_fragment -> {
//                    Thread(Runnable {
//                        try {
//                            FirebaseInstanceId.getInstance().deleteInstanceId()
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//                    }).start()
                    navController.navigate(R.id.chooseAreaFragment)
                }
                R.id.inquiry_fragment -> {
                    navController.navigate(R.id.inquiryFragment)
                }
//                R.id.engineering_records_fragment -> {
//                    if (PreferencesHelper(this).isRegistered)
//                        navController.navigate(R.id.engineeringRecordsDetailsFragment)
//                    else {
//                        val bundle: Bundle = Bundle()
//                        bundle.putInt("type", Constants.RECORDS)
//                        navController.navigate(R.id.mobileFragment, bundle)
//                    }
//                }
//                R.id.update_data_fragment -> {
//                    if (PreferencesHelper(this).isRegistered)
//                        navController.navigate(R.id.updateDataVerificationFragment)
//                    else {
//                        val bundle: Bundle = Bundle()
//                        bundle.putInt("type", Constants.UPDATE_DATA)
//                        navController.navigate(R.id.mobileFragment, bundle)
//                    }
//                }
                R.id.complaints_fragment -> {
                    Toast.makeText(this, getString(R.string.closed_complaints), Toast.LENGTH_SHORT).show()

//                    if (PreferencesHelper(this).isRegistered)
//                        navController.navigate(R.id.complaintsFragment)
//                    else {
//                        val bundle: Bundle = Bundle()
//                        bundle.putInt("type", Constants.COMPLAINTS)
//                        navController.navigate(R.id.mobileFragment, bundle)
//                    }
                }
                R.id.about_fragment -> {
                    navController.navigate(R.id.aboutFragment)
                }
                R.id.about_app_fragment -> {
                    navController.navigate(R.id.aboutAppFragment)
                }
                R.id.settings_fragment -> {
                    navController.navigate(R.id.settingsFragment)
                }
                R.id.contactus_fragment -> {
                    (nav_view as NavigationView).getHeaderView(0).findViewById<Button>(R.id.tvMobileNumber).call(Constants.CALL_CENTER, this)
//                    val intent = Intent(Intent.ACTION_SENDTO)
//                    val uriText = "mailto:" + Uri.encode("info@neqabty.com")
//                    val uri = Uri.parse(uriText)
//                    intent.setData(uri)
//                    if (intent.resolveActivity(packageManager) != null)
//                        startActivity(intent)
                }
//                R.id.aboutapp_fragment -> {
//                }
//                R.id.help_fragment -> {
//                }
//                R.id.logout_fragment -> {
//                    PreferencesHelper(this).isRegistered = false
//                    invalidateOptionsMenu()
//                } // TODO
            }
            (drawer_layout as DrawerLayout).closeDrawer(GravityCompat.START)
            true
        }

        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.homeFragment)
                supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(true)
                    setHomeAsUpIndicator(R.mipmap.menu_ic)
                }
            else
                supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(true)
                    setHomeAsUpIndicator(R.drawable.ic_up)
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

    private fun setJWT(){
        Constants.JWT = PreferencesHelper(this).jwt ?: ""
    }

    private fun getToken() { // TODO
        FirebaseApp.initializeApp(this)
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful)
                        getToken()
                    else {
                        newToken = task.result?.token!!
                        if (!newToken.equals(PreferencesHelper(this).token)) // Token has been changed
                        {
                            if (PreferencesHelper(this).mobile.isNotBlank()) { // visitor or client
                                mainViewModel.login(PreferencesHelper(this).mobile, PreferencesHelper(this).user, newToken, PreferencesHelper(this))
                            }
                        }
                    }
                })
    }

//region//

    private fun getMinSupportedVersion(){
        mainViewModel.viewState.observe(this, Observer {
            if (it != null) {
                it.appVersion?.let {
                    if (BuildConfig.VERSION_CODE < it) {
                        if (!isAlertShown)
                            showAlert()
                    }
                }
            }
        })
        mainViewModel.errorState.observe(this, Observer { error ->
            mainViewModel.getAppVersion()
        })
        mainViewModel.getAppVersion()
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
                finishAffinity()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val notificationsItem = menu?.findItem(R.id.notifications_fragment)
        val favoritesItem = menu?.findItem(R.id.favorites_fragment)
        val searchItem = menu?.findItem(R.id.search_fragment)
//        val logoutItem = menu?.findItem(R.id.logout_fragment)

        var currentFragment =
                (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).childFragmentManager.fragments[0]
        notificationsItem?.isVisible = currentFragment is HasHomeOptionsMenu &&
                PreferencesHelper(this).isRegistered // && PreferencesHelper(this).notificationsCount != 0
//        logoutItem?.isVisible = currentFragment is HasHomeOptionsMenu &&
//                PreferencesHelper(this).isRegistered
        ivHeaderLogo?.visibility = if (currentFragment is HasHomeOptionsMenu) View.VISIBLE else View.GONE
        favoritesItem?.isVisible = currentFragment is HasMedicalOptionsMenu // || currentFragment is HasFavoriteOptionsMenu
//        searchItem?.isVisible = currentFragment is HasMedicalOptionsMenu//TODO

//        if (currentFragment is HasFavoriteOptionsMenu)
//            favoritesItem?.setIcon(currentFragment.renderFav())

        var navigationView: NavigationView = (nav_view as NavigationView)
        var navMenu = navigationView.menu
//        navMenu.findItem(R.id.logout_fragment)?.isVisible = PreferencesHelper(this).isRegistered
        (nav_view as NavigationView).getHeaderView(0).findViewById<Button>(R.id.bLogout).setOnClickListener {
            PreferencesHelper(this).isRegistered = false
            PreferencesHelper(this).user = ""
            PreferencesHelper(this).name = ""
            PreferencesHelper(this).mobile = ""
            PreferencesHelper(this).jwt = ""
            PreferencesHelper(this).token = ""
            PreferencesHelper(this).notificationsCount = 0
            Thread(Runnable {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }).start()
            invalidateOptionsMenu()

            (drawer_layout as DrawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            Navigation.findNavController(this, R.id.container)
                    .navigate(R.id.openLoginFragment)
        }

        if (PreferencesHelper(this).name.isNotEmpty()) {
            (nav_view as NavigationView).getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).visibility = View.VISIBLE
            (nav_view as NavigationView).getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).setText(Html.fromHtml(getString(R.string.menu_memberName, PreferencesHelper(this).name)))
        } else
            (nav_view as NavigationView).getHeaderView(0).findViewById<TextView>(R.id.tvMemberName).visibility = View.GONE

        if (PreferencesHelper(this).user.isNotEmpty()) {
            (nav_view as NavigationView).getHeaderView(0).findViewById<TextView>(R.id.tvMemberNumber).visibility = View.VISIBLE
            (nav_view as NavigationView).getHeaderView(0).findViewById<TextView>(R.id.tvMemberNumber).setText(Html.fromHtml(getString(R.string.menu_syndicateNumber, PreferencesHelper(this).user)))
        } else
            (nav_view as NavigationView).getHeaderView(0).findViewById<TextView>(R.id.tvMemberNumber).visibility = View.GONE

        (nav_view as NavigationView).getHeaderView(0).findViewById<TextView>(R.id.tvMobileNumber).setText(Html.fromHtml(getString(R.string.menu_phoneNumber, PreferencesHelper(this).mobile)))

        (nav_view as NavigationView).getHeaderView(0).visibility = if (PreferencesHelper(this).mobile.isNotEmpty()) View.VISIBLE else View.GONE

        if (!PreferencesHelper(this).mobile.isNotEmpty())
            (nav_view as NavigationView).getChildAt(0).setPadding(0, 100, 0, 0)
        else
            (nav_view as NavigationView).getChildAt(0).setPadding(0, 0, 0, 0)

        val tvBadge = notificationsItem?.actionView?.findViewById<TextView>(R.id.tvBadge)
        if (PreferencesHelper(this).notificationsCount == 0) tvBadge?.visibility = View.INVISIBLE
        else {
            tvBadge?.visibility = View.VISIBLE
            tvBadge?.setText(PreferencesHelper(this).notificationsCount.toString())
        }
        val ivNotification = notificationsItem?.actionView?.findViewById<ImageView>(R.id.ivNotification)
        ivNotification?.setOnClickListener {
            Navigation.findNavController(this, R.id.container)
                    .navigate(R.id.notificationsFragment)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var currentFragment =
                (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).childFragmentManager.fragments[0]
        when (item?.itemId) {
            R.id.notifications_fragment -> {
                Navigation.findNavController(this, R.id.container)
                        .navigate(R.id.notificationsFragment)
            }
//            R.id.logout_fragment -> {
//                PreferencesHelper(this).isRegistered = false
//                PreferencesHelper(this).user = ""
//                PreferencesHelper(this).notificationsCount = 0
//                invalidateOptionsMenu()
//            }
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
//endregion//
}
