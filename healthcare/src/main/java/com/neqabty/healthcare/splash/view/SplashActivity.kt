package com.neqabty.healthcare.splash.view

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.BuildConfig
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.DeviceUtils
import com.neqabty.healthcare.core.utils.PushNotificationsWrapper
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private lateinit var loading: AlertDialog
    private val SPLASH_DISPLAY_LENGTH: Long = 200L
    private val splashViewModel: SplashViewModel by viewModels()
    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        if (DeviceUtils().isDeviceRooted() && !BuildConfig.DEBUG)
            showAlertDialogAndExitApp(getString(R.string.rooted))
        else
            splashViewModel.appConfig()

        splashViewModel.appConfig.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        loading.show()
                    }
                    Status.SUCCESS -> {
                        loading.dismiss()
                        val apiConfigurations = resource.data?.apiConfigurations
                        val androidVersion = apiConfigurations?.getOrNull(0)?.androidVersion?.toInt()

                        if (androidVersion != null && androidVersion <= BuildConfig.VERSION_CODE) {
                            PushNotificationsWrapper().getToken(this)
                            Handler().postDelayed({
                                val mainIntent = Intent(this, getTheNextActivityFromSplash())
                                startActivity(mainIntent)
                                finishAffinity()
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
        val appPackageName = packageName
        val marketUrl = "market://details?id=$appPackageName"
        val webUrl = "https://play.google.com/store/apps/details?id=$appPackageName"

        val alertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.alert)
            .setMessage(R.string.new_update)
            .setCancelable(false)
            .setPositiveButton(R.string.ok_btn) { dialog, _ ->
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(marketUrl)))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)))
                }
                showUpdateAppAlertDialog()
            }
            .create()

        alertDialog.show()
    }

    private fun showAlertDialogAndExitApp(message: String) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.alert)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.agree) { dialog, _ ->
                dialog.dismiss()
                finishAffinity()
            }
            .create()

        alertDialog.show()
    }
}