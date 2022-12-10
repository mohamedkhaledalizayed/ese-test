package com.neqabty.recruitment.modules.languages


import android.os.Bundle
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.databinding.ActivityLanguagesBinding

class LanguagesActivity : BaseActivity<ActivityLanguagesBinding>() {

    override fun getViewBinding() = ActivityLanguagesBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "لغات المهندس")
    }
}