package com.neqabty.healthcare.commen.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityDependantsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DependantsActivity : BaseActivity<ActivityDependantsBinding>() {
    override fun getViewBinding() = ActivityDependantsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.dependants)
        initializeViews()
    }

    private fun initializeViews() {
        binding.bNext.setOnClickListener {
            navigate()
        }

        binding.bSkip.setOnClickListener {
            navigate()
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