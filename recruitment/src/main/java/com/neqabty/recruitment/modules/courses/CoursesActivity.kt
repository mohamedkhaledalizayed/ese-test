package com.neqabty.recruitment.modules.courses


import android.os.Bundle
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.databinding.ActivityCoursesBinding

class CoursesActivity : BaseActivity<ActivityCoursesBinding>() {

    override fun getViewBinding() = ActivityCoursesBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "الدورات التدريبية")

    }
}