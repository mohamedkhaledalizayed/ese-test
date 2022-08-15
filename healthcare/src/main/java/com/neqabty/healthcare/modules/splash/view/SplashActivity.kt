package com.neqabty.healthcare.modules.splash.view

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySplashBinding
import com.neqabty.healthcare.modules.checkaccountstatus.view.CheckAccountActivity
import com.neqabty.healthcare.modules.home.presentation.view.homescreen.HomeActivity
import com.neqabty.healthcare.modules.syndicates.presentation.view.homescreen.SyndicateActivity
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.core.utils.DeviceUtils
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

        if (DeviceUtils().isDeviceRooted() || DeviceUtils().isProbablyAnEmulator()){
            showAlertDialogAndExitApp(getString(R.string.rooted))
        }else{
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
                        if (resource.data!!.apiConfigurations[0].androidVersion.toInt() <= 160){
                            Handler().postDelayed(Runnable {
                                if (sharedPreferences.mobile.isNotEmpty()){
                                    val mainIntent = Intent(this@SplashActivity, SyndicateActivity::class.java)
                                    startActivity(mainIntent)
                                    finish()
                                }else{
                                    val mainIntent = Intent(this@SplashActivity, CheckAccountActivity::class.java)
                                    startActivity(mainIntent)
                                    finish()
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
}