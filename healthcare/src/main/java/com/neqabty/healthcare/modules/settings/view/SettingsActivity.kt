package com.neqabty.healthcare.modules.settings.view

import android.os.Bundle
import com.neqabty.core.ui.BaseActivity
import com.neqabty.meganeqabty.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {

    override fun getViewBinding() = ActivitySettingsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}