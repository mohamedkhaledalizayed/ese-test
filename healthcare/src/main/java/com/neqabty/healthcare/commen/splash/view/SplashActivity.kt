package com.neqabty.healthcare.commen.splash.view

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.ActivitySplashBinding
import com.neqabty.healthcare.commen.checkaccountstatus.view.CheckAccountActivity
import com.neqabty.healthcare.commen.landing.view.LandingPageActivity
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.SehaHomeActivity
import com.neqabty.healthcare.core.utils.DeviceUtils
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

        if (DeviceUtils().isDeviceRooted() || DeviceUtils().isProbablyAnEmulator()) {
            showAlertDialogAndExitApp(getString(R.string.rooted))
        } else {
            splashViewModel.appConfig()
        }

        splashViewModel.appConfig.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        loading.show()
                    }
                    Status.SUCCESS -> {
                        loading.dismiss()
                        if (resource.data!!.apiConfigurations[0].androidVersion.toInt() <= 190) {
                            Handler().postDelayed(Runnable {
                                if (sharedPreferences.isAuthenticated) {
                                    if (sharedPreferences.isSyndicateMember) {
                                        val mainIntent = Intent(
                                            this@SplashActivity,
                                            com.neqabty.healthcare.mega.home.view.MegaHomeActivity::class.java
                                        )
                                        startActivity(mainIntent)
                                        finish()
                                    } else {
                                        val mainIntent =
                                            Intent(this@SplashActivity, SehaHomeActivity::class.java)
                                        startActivity(mainIntent)
                                        finish()
                                    }
                                } else {
                                    if (sharedPreferences.mobile.isEmpty()) {
                                        val mainIntent = Intent(
                                            this@SplashActivity,
                                            CheckAccountActivity::class.java
                                        )
                                        startActivity(mainIntent)
                                        finish()
                                    } else {
                                        val mainIntent = Intent(
                                            this@SplashActivity,
                                            LandingPageActivity::class.java
                                        )
                                        startActivity(mainIntent)
                                        finish()
                                    }
                                }
                            }, SPLASH_DISPLAY_LENGTH)
                        } else {
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

}