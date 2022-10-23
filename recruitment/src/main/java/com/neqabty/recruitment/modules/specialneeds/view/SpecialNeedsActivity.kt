package com.neqabty.recruitment.modules.specialneeds.view


import android.os.Bundle
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.databinding.ActivitySpecialNeedsBinding

class SpecialNeedsActivity : BaseActivity<ActivitySpecialNeedsBinding>() {

    override fun getViewBinding() = ActivitySpecialNeedsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}