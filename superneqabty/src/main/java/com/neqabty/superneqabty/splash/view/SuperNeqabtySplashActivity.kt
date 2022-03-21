package com.neqabty.superneqabty.splash.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.superneqabty.BuildConfig
import com.neqabty.superneqabty.core.ui.BaseActivity
import com.neqabty.superneqabty.core.utils.Status
import com.neqabty.superneqabty.databinding.ActivitySuperNeqabtyMainBinding
import com.neqabty.superneqabty.home.view.homescreen.HomeActivity
import com.neqabty.superneqabty.home.view.homescreen.HomeViewModel
import com.neqabty.superneqabty.syndicates.presentation.view.homescreen.SyndicateActivity
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
            .setMessage("من فضلك انتظر...")
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
                        if (resource.data!!.apiConfigurations[0].androidVersion == "158"){
                            Handler().postDelayed(Runnable {
                                val mainIntent = Intent(this@SuperNeqabtySplashActivity, if(sharedPreferences.mainSyndicate == -1) SyndicateActivity::class.java else HomeActivity::class.java)
                                startActivity(mainIntent)
                                finish()
                            }, SPLASH_DISPLAY_LENGTH)
                        }else{
                            Toast.makeText(this, "يوجد اصدار جديد", Toast.LENGTH_LONG).show()
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
}