package com.neqabty.healthcare.core.packages


import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityUploadFrontNationalidBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UploadFrontNationalid : BaseActivity<ActivityUploadFrontNationalidBinding>() {

    override fun getViewBinding() = ActivityUploadFrontNationalidBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "بيانات الحساب")

        binding.bNext.setOnClickListener {
            startActivity(Intent(this, UploadBackNationalid::class.java))
        }
    }

}