package com.neqabty.recruitment.modules.home.view


import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}