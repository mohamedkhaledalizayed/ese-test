package com.neqabty.recruitment.modules.qualifications.view


import android.os.Bundle
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.databinding.ActivityQualificationsBinding

class QualificationsActivity : BaseActivity<ActivityQualificationsBinding>() {

    override fun getViewBinding() = ActivityQualificationsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "المؤهلات الدراسية")
    }
}