package com.neqabty.superneqabty.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.neqabty.signup.databinding.ActivitySignupBinding
import com.neqabty.superneqabty.BuildConfig
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.core.ui.BaseActivity
import com.neqabty.superneqabty.databinding.ActivitySuperNeqabtyMainBinding
import com.neqabty.superneqabty.home.view.homescreen.HomeActivity
import com.neqabty.superneqabty.syndicates.presentation.view.homescreen.SyndicateActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperNeqabtySplashActivity : BaseActivity<ActivitySuperNeqabtyMainBinding>() {

    private val SPLASH_DISPLAY_LENGTH: Long = if(BuildConfig.DEBUG) 30L else 3000L

    override fun getViewBinding() = ActivitySuperNeqabtyMainBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        Handler().postDelayed(Runnable {
            val mainIntent = Intent(this@SuperNeqabtySplashActivity, if(sharedPreferences.mainSyndicate == -1) SyndicateActivity::class.java else HomeActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}