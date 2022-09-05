package com.neqabty.recruitment.modules.resume


import android.content.Intent
import android.os.Bundle
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.databinding.ActivityResumeBinding
import com.neqabty.recruitment.modules.personalinfo.view.PersonalInfoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeActivity : BaseActivity<ActivityResumeBinding>() {

    override fun getViewBinding() = ActivityResumeBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "السيرة الذاتية")

        binding.personalInfo.setOnClickListener {
            startActivity(Intent(this, PersonalInfoActivity::class.java))
        }
    }
}