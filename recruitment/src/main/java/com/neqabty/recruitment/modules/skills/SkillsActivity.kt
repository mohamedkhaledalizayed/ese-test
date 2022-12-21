package com.neqabty.recruitment.modules.skills


import android.os.Bundle
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.databinding.ActivitySkillsBinding

class SkillsActivity : BaseActivity<ActivitySkillsBinding>() {

    override fun getViewBinding() = ActivitySkillsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "مهارات المهندس")
    }

}