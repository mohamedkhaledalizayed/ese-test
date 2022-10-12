package com.neqabty.healthcare.modules.splash.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.core.data.Constants.MEGA_HOME
import com.neqabty.core.data.Constants.from
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySplashBinding
import com.neqabty.healthcare.modules.checkaccountstatus.view.CheckAccountActivity
import com.neqabty.healthcare.modules.home.presentation.view.homescreen.HomeActivity
import com.neqabty.healthcare.modules.profile.presentation.ProfileActivity
import com.neqabty.healthcare.modules.syndicates.presentation.view.homescreen.SyndicateActivity
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.core.utils.Constants
import com.neqabty.meganeqabty.core.utils.DeviceUtils
import com.neqabty.news.modules.home.presentation.view.newslist.NewsListActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private lateinit var loading: AlertDialog
    private val SPLASH_DISPLAY_LENGTH: Long = 2000L
    private val splashViewModel: SplashViewModel by viewModels()
    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()


        splashViewModel.appConfig.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        loading.show()
                    }
                    Status.SUCCESS -> {
                        loading.dismiss()
                        if (resource.data!!.apiConfigurations[0].androidVersion.toInt() <= 170){
                            Handler().postDelayed(Runnable {
                                if (sharedPreferences.isAuthenticated){
                                    if (sharedPreferences.isSyndicateMember){
                                        val mainIntent = Intent(this@SplashActivity, com.neqabty.meganeqabty.home.view.homescreen.HomeActivity::class.java)
                                        startActivity(mainIntent)
                                    }else{
                                        val mainIntent = Intent(this@SplashActivity, HomeActivity::class.java)
                                        startActivity(mainIntent)
                                        finish()
                                    }
                                }else{
                                    if (sharedPreferences.mobile.isEmpty()){
                                        val mainIntent = Intent(this@SplashActivity, CheckAccountActivity::class.java)
                                        startActivity(mainIntent)
                                    }else{
                                        val mainIntent = Intent(this@SplashActivity, SyndicateActivity::class.java)
                                        startActivity(mainIntent)
                                    }
                                }
                            }, SPLASH_DISPLAY_LENGTH)
                        }else{
                            showUpdateAppAlertDialog()
                        }
                    }
                    Status.ERROR -> {
                        loading.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }


    private fun showUpdateAppAlertDialog() {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(getString(R.string.new_update))
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.ok_btn)
        ) { dialog, _ ->
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
            showUpdateAppAlertDialog()
        }
        alertDialog.show()

    }

    private fun showAlertDialogAndExitApp(message: String) {

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            finishAffinity()
        }

        alertDialog.show()
    }


    override fun onResume() {
        super.onResume()
        if (from == MEGA_HOME){
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            intent.putExtra("from", MEGA_HOME)
            startActivity(intent)
        }else{
            if (DeviceUtils().isDeviceRooted() || DeviceUtils().isProbablyAnEmulator()){
                showAlertDialogAndExitApp(getString(R.string.rooted))
            }else{
                splashViewModel.appConfig()
            }
        }
    }
}