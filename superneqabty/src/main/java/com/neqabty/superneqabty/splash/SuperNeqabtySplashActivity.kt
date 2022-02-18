package com.neqabty.superneqabty.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.syndicates.presentation.view.homescreen.SyndicateActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperNeqabtySplashActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH: Long = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_neqabty_main)

        Handler().postDelayed(Runnable {
            val mainIntent = Intent(this@SuperNeqabtySplashActivity, SyndicateActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}