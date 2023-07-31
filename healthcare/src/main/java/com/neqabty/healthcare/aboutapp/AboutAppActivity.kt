package com.neqabty.healthcare.aboutapp

import android.content.Intent
import android.net.Uri
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

        binding.backBtn.setOnClickListener { finish() }
    }


    override fun onResume() {
        super.onResume()
        initializeViews()
    }


    private fun initializeViews() {
        binding.neqabtyUrl.setOnClickListener { openUrl("https://www.neqabty.com/") }
        binding.instagramContainer.setOnClickListener { openUrl("https://instagram.com/neqabty.2030?igshid=NTc4MTIwNjQ2YQ==") }
        binding.facebookContainer.setOnClickListener { openUrl("https://www.facebook.com/neqabty?mibextid=ZbWKwL") }
//        binding.twitterContainer.setOnClickListener { openUrl("") }
        binding.linkedinContainer.setOnClickListener { openUrl("https://www.linkedin.com/company/neqabty/") }
    }

    private fun openUrl(url: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }

}