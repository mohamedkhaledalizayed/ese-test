package com.neqabty

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.neqabty.domain.usecases.GetUserRegistered
import com.neqabty.presentation.util.PreferencesHelper
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var getUserRegistered: GetUserRegistered

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            customiseStatusbar()

        mainViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainViewModel::class.java)

        setContentView(R.layout.main_activity)
        setupViews()
        verifyAvailableNetwork()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onSupportNavigateUp(): Boolean {
        findNavController(this, R.id.container).navigateUp()
        return super.onSupportNavigateUp()
    }

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
        builder.setTitle(getString(R.string.no_connection_title))
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

    private fun setupViews() {
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.mipmap.menu_ic)
        }
    }

    private fun startActivities() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main)
        val navController = findNavController(this, R.id.container)
        graph.setDefaultArguments(intent.extras)

        if (PreferencesHelper(this).isSyndicateChosen())
            graph.startDestination = R.id.homeFragment
        else
            graph.startDestination = R.id.syndicatesFragment

        navHostFragment.navController.graph = graph

        //////////////////////////////////
//TODO replace
        setupDrawerToggle()
        NavigationUI.setupWithNavController(nav_view, navController)

        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        //////////////////////////////////
        nav_view.setNavigationItemSelectedListener {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            when (it.itemId) {
                R.id.homeFragment -> {
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    navController.navigate(R.id.homeFragment)
                }
                R.id.news_fragment -> {
                    navController.navigate(R.id.newsFragment)
                }
                R.id.trips_fragment -> {
                    navController.navigate(R.id.tripsFragment)
                }
                R.id.claiming_fragment -> {
                    if (PreferencesHelper(this).mobile.isBlank())
                        navController.navigate(R.id.mobileFragment)
                    else
                        navController.navigate(R.id.claimingFragment)
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
//                R.id.logout_fragment -> {
//                }

            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        setupActionBarWithNavController(this, navController)
        supportFragmentManager.beginTransaction().setPrimaryNavigationFragment(navHostFragment).commit()
    }

    private fun setupDrawerToggle() {
        val mDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.home_title, R.string.home_title) {}
        drawer_layout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
    }

    private fun getToken() {//TODO
        FirebaseApp.initializeApp(this)
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful)
                        return@OnCompleteListener
                    val token = task.result?.token
                    PreferencesHelper(this).token = token
                    if (PreferencesHelper(this).mobile.isNotBlank()) {//TODO register
                        mainViewModel.registerUser(PreferencesHelper(this).mobile,PreferencesHelper(this).mainSyndicate,PreferencesHelper(this).subSyndicate,PreferencesHelper(this).token,PreferencesHelper(this))
                    }

                    Log.d("Toooken", token)
                })
    }

    //region//
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun customiseStatusbar() {
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setBackgroundDrawableResource(R.mipmap.full_bg)
    }
    //endregion//
}
