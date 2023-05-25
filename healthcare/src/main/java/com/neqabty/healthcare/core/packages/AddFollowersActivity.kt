package com.neqabty.healthcare.core.packages


import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityAddFollowersBinding

class AddFollowersActivity : BaseActivity<ActivityAddFollowersBinding>() {

    override fun getViewBinding() = ActivityAddFollowersBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "تسجيل الاشتراك")


    }

}