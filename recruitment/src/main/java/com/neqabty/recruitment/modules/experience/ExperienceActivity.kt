package com.neqabty.recruitment.modules.experience


import android.os.Bundle
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.databinding.ActivityExperienceBinding

class ExperienceActivity : BaseActivity<ActivityExperienceBinding>() {

    override fun getViewBinding() = ActivityExperienceBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "الخبرات السابقة للمهندس")
    }

}