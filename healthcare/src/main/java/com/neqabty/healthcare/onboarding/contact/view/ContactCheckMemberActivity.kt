package com.neqabty.healthcare.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.core.utils.isNationalIdValid
import com.neqabty.healthcare.databinding.ActivityContactCheckMemberBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactCheckMemberActivity : BaseActivity<ActivityContactCheckMemberBinding>() {
    override fun getViewBinding() = ActivityContactCheckMemberBinding.inflate(layoutInflater)
    val contactCheckMemberViewModel: ContactCheckMemberViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.subscribe_in_contact)
        observeOnCheckMemberStatus()
        initializeViews()
    }

    private fun observeOnCheckMemberStatus() {
        contactCheckMemberViewModel.checkMemberStatus.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        if (resource.data != null){
                            when(resource.data.status){
                                3 -> {//the user didn’t have a contact account with this national id and he will register
                                    showTermsDialog()
                                }
                                5 -> {// has ocr
                                    val mainIntent = Intent(
                                        this,
                                        ReviewYourDataActivity::class.java
                                    )
                                    mainIntent.putExtra("address", resource.data.ocrs?.get(1)?.result?.extractedInfo?.street + " "+ resource.data.ocrs?.get(1)?.result?.extractedInfo?.governorate)
                                    startActivity(mainIntent)
                                }
                                else -> { // 1,2,4 has account
                                    Toast.makeText(this@ContactCheckMemberActivity, resource.data.message, Toast.LENGTH_LONG).show()
                                    startActivity(Intent(this@ContactCheckMemberActivity, getHomeActivity()))
                                    finishAffinity()
                                }
                            }
                        }else{
                            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                    }
                }
            }
        }
    }

    private fun initializeViews() {
//        sharedPreferences.nationalId = "27501252801236"
        if (sharedPreferences.nationalId.isNotEmpty()) {
            binding.etNationalId.setText(sharedPreferences.nationalId)
            binding.etNationalId.isEnabled = false
        }

        binding.bHome.setOnClickListener {
            navigate()
        }

        binding.bNext.setOnClickListener {
            if(!binding.etNationalId.text.toString().isNationalIdValid()){
                Toast.makeText(this, getString(R.string.enter_national_id), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            contactCheckMemberViewModel.checkMemberStatus(nationalId = binding.etNationalId.text.toString())
        }
    }


    private fun showWaitingProgressbar() {
        binding.clProgress.visibility = View.VISIBLE
        val totalProgress = 100
        val durationInMillis = 30000L // 1 minute in milliseconds

        val countDownTimer = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val progress =
                    ((durationInMillis - millisUntilFinished) * totalProgress / durationInMillis).toInt()
                if(progress % 4 == 0) runTextviewAnimation(progress)
            }

            override fun onFinish() {
                binding.clProgress.visibility = View.GONE
                contactCheckMemberViewModel.checkMemberStatus(nationalId = binding.etNationalId.text.toString())
            }
        }

        countDownTimer.start()
    }

    private fun runTextviewAnimation(progress: Int) {
        binding.tvProgress.text = when ((progress / 4 % 4)) {
            1 -> getString(R.string.contact_loading_two)
            2 -> getString(R.string.contact_loading_three)
            3 -> getString(R.string.contact_loading_four)
            else -> {
                getString(R.string.contact_loading_one)
            }
        }
    }

    private fun showTermsDialog() {
        val bottomSheetFragment = ContactTermsBottomSheet()
        bottomSheetFragment.onActionListener = object: ContactTermsBottomSheet.OnActionListener {
            override fun onAcceptListener() {
                val mainIntent = Intent(
                    this@ContactCheckMemberActivity,
                    UploadIdFrontActivity::class.java
                )
                startActivity(mainIntent)
            }

            override fun onDismissListener() {
            }
        }
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    //region
    private fun navigate() {
        val mainIntent = Intent(
            this,
            getHomeActivity()
        )
        startActivity(mainIntent)
        finishAffinity()
    }

    override fun onBackPressed() {
        closeApp()
    }
// endregion
}