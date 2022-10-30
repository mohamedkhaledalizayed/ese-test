package com.neqabty.shealth.sustainablehealth.aboutapp

import android.os.Bundle
import com.neqabty.shealth.R
import com.neqabty.shealth.core.ui.BaseActivity
import com.neqabty.shealth.databinding.ActivityAboutAppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutAppActivity : BaseActivity<ActivityAboutAppBinding>() {

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