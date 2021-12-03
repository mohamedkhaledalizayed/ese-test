package com.neqabty.trips.modules.home.presentation.view.homescreen

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.neqabty.trips.R
import com.neqabty.trips.core.ui.BaseActivity
import com.neqabty.trips.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding>() {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeViewModel.getCities()
        homeViewModel.getDestinations()
        homeViewModel.cities.observe(this){
            Log.e("Cities: ",it.toString())
        }
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}