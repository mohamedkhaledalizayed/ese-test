package com.neqabty.superneqabty.aboutapp

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.databinding.ActivityAboutAppBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutAppActivity : AppCompatActivity() {
    
    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityAboutAppBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutAppBinding.inflate(layoutInflater)

        setContentView(binding.root)

        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        toolbar = findViewById<Toolbar>(R.id.toolbar)
    }


    override fun onResume() {
        super.onResume()
        initializeViews()
    }


    private fun initializeViews() {
    }

}