package com.neqabty.healthcare.commen.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
        clearAllSelections()

        binding.clDependentNum0.setOnClickListener{
            clearAllSelections()
            selectDependatsNumber(binding.ivDependentNum0, binding.tvDependantsNum0)
        }
        binding.clDependentNum1.setOnClickListener{
            clearAllSelections()
            selectDependatsNumber(binding.ivDependentNum1, binding.tvDependantsNum1)
        }
        binding.clDependentNum2.setOnClickListener{
            clearAllSelections()
            selectDependatsNumber(binding.ivDependentNum2, binding.tvDependantsNum2)
        }
        binding.clDependentNum3.setOnClickListener{
            clearAllSelections()
            selectDependatsNumber(binding.ivDependentNum3, binding.tvDependantsNum3)
        }

        binding.bNext.setOnClickListener {
            navigate()
        }

        binding.bSkip.setOnClickListener {
            navigate()
        }
    }

    //region
    private fun selectDependatsNumber(imageView: ImageView, textView: TextView){
        imageView.setImageDrawable(resources.getDrawable(R.drawable.ellipse_selected))
        textView.textSize = 30F
    }

    private fun clearAllSelections() {
        binding.ivDependentNum0.setImageDrawable(resources.getDrawable(R.drawable.ellipse_unselected))
        binding.ivDependentNum1.setImageDrawable(resources.getDrawable(R.drawable.ellipse_unselected))
        binding.ivDependentNum2.setImageDrawable(resources.getDrawable(R.drawable.ellipse_unselected))
        binding.ivDependentNum3.setImageDrawable(resources.getDrawable(R.drawable.ellipse_unselected))

        binding.tvDependantsNum0.textSize = 10F
        binding.tvDependantsNum1.textSize = 10F
        binding.tvDependantsNum2.textSize = 10F
        binding.tvDependantsNum3.textSize = 10F
    }

    private fun navigate() {
        val mainIntent = Intent(
            this,
            ReferenceNumberActivity::class.java
        )
        startActivity(mainIntent)
        finish()
    }
// endregion
}