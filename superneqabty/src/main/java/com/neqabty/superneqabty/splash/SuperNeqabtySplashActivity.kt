package com.neqabty.superneqabty.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.neqabty.signup.databinding.ActivitySignupBinding
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.databinding.ActivitySuperNeqabtyMainBinding
import com.neqabty.superneqabty.syndicates.presentation.view.homescreen.SyndicateActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperNeqabtySplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuperNeqabtyMainBinding
    private val SPLASH_DISPLAY_LENGTH: Long = 30L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperNeqabtyMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed(Runnable {
            val mainIntent = Intent(this@SuperNeqabtySplashActivity, SyndicateActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}