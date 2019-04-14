package com.neqabty

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.WindowManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.neqabty.presentation.util.*
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            customiseStatusbar()
        setContentView(R.layout.main_activity)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainViewModel::class.java)

        setSupportActionBar(toolbar)
//        verifyAvailableNetwork()//TODO
        getToken()
        startActivities()
        val notificationId: String? = intent?.extras?.getString("notificationId")
        notificationId?.let {
            val args = Bundle()
            args.putString("notificationId", it)
            Navigation.findNavController(this, R.id.container).navigate(R.id.notificationDetailsFragment, args)
        }
        mainViewModel.viewState.observe(this, Observer {
            invalidateOptionsMenu()
        })
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private fun verifyAvailableNetwork() {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            getToken()
            startActivities()
        } else {
            showConnectionAlert()
        }
    }

    private fun showConnectionAlert() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.no_connection_msg))
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.no_connection_retry)) { dialog, which ->
            verifyAvailableNetwork()
        }
        builder.setNegativeButton(getString(R.string.no_connection_cancel)) { dialog, which ->
            finishAffinity()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun startActivities() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main)
        val navController = Navigation.findNavController(this, R.id.container)
//        graph.desetDefaultArguments(intent.extras)

        if (PreferencesHelper(this).isSyndicateChosen())//TODO
            graph.startDestination = R.id.homeFragment
        else
            graph.startDestination = R.id.syndicatesFragment

        navHostFragment.navController.graph = graph
        supportFragmentManager.beginTransaction().setPrimaryNavigationFragment(navHostFragment).commit()
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
        nav_view.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.syndicatesFragment), drawer_layout)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        //////////////////////////////////
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        nav_view.setNavigationItemSelectedListener {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            when (it.itemId) {
                R.id.home_fragment -> {
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                R.id.news_fragment -> {
                    navController.navigate(R.id.newsFragment)
                }
                R.id.trips_fragment -> {
                    navController.navigate(R.id.tripsFragment)
                }
                R.id.claiming_fragment -> {//TODO
                    if (PreferencesHelper(this).isRegistered)
                        navController.navigate(R.id.claimingFragment)
                    else
                        navController.navigate(R.id.mobileFragment)
                }
                R.id.medical_fragment -> {
                    navController.navigate(R.id.chooseAreaFragment)
                }
                R.id.inquiry_fragment -> {
                    navController.navigate(R.id.inquiryFragment)
                }
                R.id.about_fragment -> {
                    navController.navigate(R.id.aboutFragment)
                }
                R.id.contactus_fragment -> {
                }
                R.id.aboutapp_fragment -> {
                }
                R.id.help_fragment -> {
                }
                R.id.logout_fragment -> {
                    PreferencesHelper(this).isRegistered = false
                    invalidateOptionsMenu()
                }//TODO
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

    }

    private fun getToken() {//TODO
        FirebaseApp.initializeApp(this)
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful)
                        return@OnCompleteListener
                    val token = task.result?.token

                    if (PreferencesHelper(this).mobile.isNotBlank() && !token.equals(PreferencesHelper(this).token)) {//TODO register
                        mainViewModel.registerUser(PreferencesHelper(this).mobile, PreferencesHelper(this).mainSyndicate, PreferencesHelper(this).subSyndicate, PreferencesHelper(this).token, PreferencesHelper(this), PreferencesHelper(this).user)
                    } else
                        PreferencesHelper(this).token = token

                    Log.d("Toooken", token)
                })
    }

    //region//

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.container), drawer_layout)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            var currentFragment = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).childFragmentManager.fragments[0];
            if (currentFragment is OnBackPressedListener)
                finishAffinity()
            super.onBackPressed()
        }
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
//        val changeAreaItem = menu?.findItem(R.id.change_area_fragment)

        var currentFragment = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).childFragmentManager.fragments[0];
        notificationsItem?.isVisible = currentFragment is HasHomeOptionsMenu && PreferencesHelper(this).isRegistered
        favoritesItem?.isVisible = currentFragment is HasMedicalOptionsMenu || currentFragment is HasFavoriteOptionsMenu
        searchItem?.isVisible = currentFragment is HasMedicalOptionsMenu

        if(currentFragment is HasFavoriteOptionsMenu)
            favoritesItem?.setIcon(currentFragment.renderFav())

        var navigationView: NavigationView = nav_view
        var navMenu = navigationView.menu
        navMenu.findItem(R.id.logout_fragment)?.isVisible = PreferencesHelper(this).isRegistered

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var currentFragment = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).childFragmentManager.fragments[0];
        when (item?.itemId) {
            R.id.notifications_fragment -> {
                Navigation.findNavController(this, R.id.container).navigate(R.id.notificationsFragment)
            }
            R.id.search_fragment -> {
                Navigation.findNavController(this, R.id.container).navigate(R.id.searchFragment)
            }
            R.id.favorites_fragment -> {
                if(currentFragment is HasMedicalOptionsMenu)
                    Navigation.findNavController(this, R.id.container).navigate(R.id.favoritesFragment)
                else if(currentFragment is HasFavoriteOptionsMenu)
                    currentFragment.toggleFav()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //endregion//
}
