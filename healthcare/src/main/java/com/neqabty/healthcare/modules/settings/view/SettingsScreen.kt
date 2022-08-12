package com.neqabty.healthcare.modules.settings.view

import android.content.Intent
import android.os.Bundle
import com.neqabty.core.ui.BaseActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.ScreenSettingsBinding
import com.neqabty.healthcare.modules.splash.view.SplashActivity
import com.neqabty.meganeqabty.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsScreen : BaseActivity<ScreenSettingsBinding>() {

    override fun getViewBinding() = ScreenSettingsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (sharedPreferences.language == "ar"){
            binding.toggleGroup.check(R.id.btn_arabic)
            binding.language.text = "اللغة"
        }else{
            binding.toggleGroup.check(R.id.btn_english)
            binding.language.text = "Language"
        }
        binding.btnArabic.setOnClickListener {
            sharedPreferences.language = "ar"
            binding.toggleGroup.check(R.id.btn_arabic)
            finish()
            startActivity(Intent(this, SettingsScreen::class.java))

        }

        binding.btnEnglish.setOnClickListener {
            sharedPreferences.language = "en"
            binding.toggleGroup.check(R.id.btn_english)
            finish()
            startActivity(Intent(this, SettingsScreen::class.java))

        }
    }

    override fun onBackPressed() {
        finishAffinity()
        startActivity(Intent(this, SplashActivity::class.java))
    }
}