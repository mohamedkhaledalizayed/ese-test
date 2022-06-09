package com.neqabty.healthcare.modules.home.presentation.view.homescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hc_home)
        homeViewModel.getProviders()
        homeViewModel.providers.observe(this){
            Log.d("medicalProviders",it.size.toString())
        }
    }
}