package com.neqabty.meganeqabty.splash.view

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.meganeqabty.BuildConfig
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.core.ui.BaseActivity
import com.neqabty.meganeqabty.core.utils.Status
import com.neqabty.meganeqabty.databinding.ActivitySuperNeqabtyMainBinding
import com.neqabty.meganeqabty.home.view.homescreen.HomeActivity
import com.neqabty.meganeqabty.syndicates.presentation.view.homescreen.SyndicateActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class SuperNeqabtySplashActivity : BaseActivity<ActivitySuperNeqabtyMainBinding>() {

    private lateinit var loading: AlertDialog
    private val SPLASH_DISPLAY_LENGTH: Long = if(BuildConfig.DEBUG) 30L else 2000L
    private val splashViewModel: SplashViewModel by viewModels()
    override fun getViewBinding() = ActivitySuperNeqabtyMainBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        splashViewModel.appConfig()
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
                                val mainIntent = Intent(this@SuperNeqabtySplashActivity, if(sharedPreferences.mainSyndicate == -1) SyndicateActivity::class.java else HomeActivity::class.java)
                                startActivity(mainIntent)
                                finish()
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
}