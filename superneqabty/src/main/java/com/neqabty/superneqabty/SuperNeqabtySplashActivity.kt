package com.neqabty.superneqabty

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperNeqabtySplashActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH: Long = 3000L
    private val viewModel: SuperNeqabtySplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_neqabty_main)
        viewModel.getSyndicateAds(1)
        viewModel.ads.observe(this){
            Toast.makeText(applicationContext,it[0].title,Toast.LENGTH_SHORT).show()
        }
        Handler().postDelayed(Runnable {
            val mainIntent = Intent(this@SuperNeqabtySplashActivity, SuperNeqabtySplashActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}