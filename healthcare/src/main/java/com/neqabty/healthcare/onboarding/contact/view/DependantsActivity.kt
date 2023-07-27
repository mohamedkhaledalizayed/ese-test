package com.neqabty.healthcare.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityDependantsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DependantsActivity : BaseActivity<ActivityDependantsBinding>() {
    var dependantsNum = -1
    override fun getViewBinding() = ActivityDependantsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.dependants)
        initializeViews()
    }

    private fun initializeViews() {
        clearAllSelections()

        binding.tvDependantsNum0.setOnClickListener{
            clearAllSelections()
            selectDependantsNumber(binding.tvDependantsNum0)
            dependantsNum = 0
        }
        binding.tvDependantsNum1.setOnClickListener{
            clearAllSelections()
            selectDependantsNumber(binding.tvDependantsNum1)
            dependantsNum = 1
        }
        binding.tvDependantsNum2.setOnClickListener{
            clearAllSelections()
            selectDependantsNumber(binding.tvDependantsNum2)
            dependantsNum = 2
        }
        binding.tvDependantsNum3.setOnClickListener{
            clearAllSelections()
            selectDependantsNumber(binding.tvDependantsNum3)
            dependantsNum = 3
        }

        binding.bNext.setOnClickListener {
            if(dependantsNum == -1){
                Toast.makeText(this, getString(R.string.enter_dependants_number), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            SubmitClientData.entity.clientInfo.dependentsNum = dependantsNum.toString()
            navigate()
        }

        binding.bSkip.setOnClickListener {
            navigate()
        }
    }

    //region
    private fun selectDependantsNumber(textView: TextView) {
        textView.setBackgroundResource(R.drawable.ellipse_selected)
        textView.setTextColor(resources.getColor(R.color.white))
        textView.textSize = 25F
    }

    private fun clearAllSelections() {
        binding.tvDependantsNum0.setBackgroundResource(R.drawable.ellipse_unselected)
        binding.tvDependantsNum0.setTextColor(resources.getColor(R.color.unselected_color))
        binding.tvDependantsNum0.textSize = 10F

        binding.tvDependantsNum1.setBackgroundResource(R.drawable.ellipse_unselected)
        binding.tvDependantsNum1.setTextColor(resources.getColor(R.color.unselected_color))
        binding.tvDependantsNum1.textSize = 10F

        binding.tvDependantsNum2.setBackgroundResource(R.drawable.ellipse_unselected)
        binding.tvDependantsNum2.setTextColor(resources.getColor(R.color.unselected_color))
        binding.tvDependantsNum2.textSize = 10F

        binding.tvDependantsNum3.setBackgroundResource(R.drawable.ellipse_unselected)
        binding.tvDependantsNum3.setTextColor(resources.getColor(R.color.unselected_color))
        binding.tvDependantsNum3.textSize = 10F
    }

    private fun navigate() {
        val mainIntent = Intent(
            this,
            ReferenceNumberActivity::class.java
        )
        startActivity(mainIntent)
    }
// endregion
}