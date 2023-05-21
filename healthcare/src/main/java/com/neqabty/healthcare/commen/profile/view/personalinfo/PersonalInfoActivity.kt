package com.neqabty.healthcare.commen.profile.view.personalinfo


import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPersonalInfoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PersonalInfoActivity : BaseActivity<ActivityPersonalInfoBinding>() {

    override fun getViewBinding() = ActivityPersonalInfoBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "البيانات الشخصية")



    }
}