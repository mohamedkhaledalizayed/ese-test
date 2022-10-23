package com.neqabty.recruitment.modules.work.view


import android.os.Bundle
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.databinding.ActivityWorkBinding

class WorkActivity : BaseActivity<ActivityWorkBinding>() {

    override fun getViewBinding() = ActivityWorkBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}