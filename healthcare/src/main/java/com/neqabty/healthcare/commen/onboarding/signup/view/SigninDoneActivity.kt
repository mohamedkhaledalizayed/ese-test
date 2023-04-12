package com.neqabty.healthcare.commen.onboarding.signup.view

import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.onboarding.uploadID.UploadIdFrontActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivitySigninDoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninDoneActivity : BaseActivity<ActivitySigninDoneBinding>() {
    override fun getViewBinding() = ActivitySigninDoneBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.signin_done)
        initializeViews()
    }

    private fun initializeViews() {
        binding.bHome.setOnClickListener {
            navigate()
        }

        binding.bCompleteProfile.setOnClickListener {
            val mainIntent = Intent(
                this,
                UploadIdFrontActivity::class.java
            )
            startActivity(mainIntent)
            finish()
        }
    }

    //region
    private fun navigate() {
        val mainIntent = Intent(
            this,
            getTheNextActivityFromSignup()
        )
        startActivity(mainIntent)
        finish()
    }
// endregion
}