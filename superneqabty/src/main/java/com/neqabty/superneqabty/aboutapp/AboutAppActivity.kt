package com.neqabty.superneqabty.aboutapp

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.neqabty.superneqabty.BuildConfig
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.core.ui.BaseActivity
import com.neqabty.superneqabty.databinding.ActivityAboutAppBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
        binding.tvVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)
    }

}