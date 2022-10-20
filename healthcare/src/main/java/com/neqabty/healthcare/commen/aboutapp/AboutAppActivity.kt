package com.neqabty.healthcare.commen.aboutapp

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityAboutAppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutAppActivity : BaseActivity<ActivityAboutAppBinding>() {
    
    private lateinit var toolbar: Toolbar

    override fun getViewBinding() = ActivityAboutAppBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupToolbar(titleResId = R.string.aboutapp_title)

    }


    override fun onResume() {
        super.onResume()
        initializeViews()
    }


    private fun initializeViews() {
        binding.tvVersion.text = getString(R.string.app_version, "1.0")
    }

}