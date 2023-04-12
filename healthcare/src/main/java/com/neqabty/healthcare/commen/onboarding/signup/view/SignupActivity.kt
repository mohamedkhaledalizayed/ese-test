package com.neqabty.healthcare.commen.onboarding.signup.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import com.neqabty.healthcare.BuildConfig
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.otp.data.model.CheckOTPBody
import com.neqabty.healthcare.auth.otp.data.model.SendOTPBody
import com.neqabty.healthcare.commen.onboarding.signup.data.SignupData
import com.neqabty.healthcare.commen.onboarding.uploadID.UploadIdFrontActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySignupMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupMainBinding>() {
    override fun getViewBinding() = ActivitySignupMainBinding.inflate(layoutInflater)
    val signupViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.signup_title)
        initializeViews()
        getSyndicates()
    }

    private fun initializeViews() {
        val adapter = SignupFragmentPagerAdapter(supportFragmentManager)
        adapter.addFragment(SignupStep1PagerFragment())
        adapter.addFragment(SignupStep2PagerFragment())
        adapter.addFragment(SignupStep3PagerFragment())
        adapter.addFragment(SignupStep4PagerFragment())

        binding.slvpSignup.adapter = adapter
        binding.slvpSignup.setSwipePagingEnabled(false)
        binding.ciSignup.setViewPager(binding.slvpSignup)

        binding.slvpSignup.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 2)
                    binding.bNext.visibility = View.GONE
                else
                    binding.bNext.visibility = View.VISIBLE
            }
        })

        binding.bNext.setOnClickListener {
            when (binding.slvpSignup.currentItem) {
                0 -> {
                    val step1Binding = (adapter.fragments[0] as SignupStep1PagerFragment).binding
                    if (!step1Binding.ccp.isValidFullNumber) {
                        Toast.makeText(
                            this@SignupActivity,
                            getString(R.string.enter_correct_phone),
                            Toast.LENGTH_LONG
                        ).show()
                        return@setOnClickListener
                    }
                    sharedPreferences.mobile = step1Binding.ccp.fullNumberWithPlus
                    sharedPreferences.mobile_without_cc = step1Binding.ccp.fullNumber
                        .substring(1, step1Binding.ccp.fullNumber.length)
                    sendOTP()
                    movePagertoNext()
                }
                1 -> {
                    if (BuildConfig.DEBUG) {
                        movePagertoNext()
                        return@setOnClickListener
                    }
                    val step2Binding = (adapter.fragments[1] as SignupStep2PagerFragment).binding
                    if (step2Binding.otvOtp.otp?.length != 5) {
                        step2Binding.otvOtp.showError()
                        Toast.makeText(
                            this@SignupActivity,
                            getString(R.string.enter_code_),
                            Toast.LENGTH_LONG
                        ).show()
                        return@setOnClickListener
                    }
                    checkOTP(step2Binding.otvOtp.otp!!)
                }
                2 -> {}
                else -> {
                    if (BuildConfig.DEBUG) {
                        val mainIntent = Intent(
                            this,
                            SigninDoneActivity::class.java
                        )
                        startActivity(mainIntent)
                        finish()
                    }
                    if (SignupData.syndicateID == Constants.NEQABTY_CODE)
                        (adapter.fragments[3] as SignupStep4PagerFragment).generalSignup()
                    else
                        (adapter.fragments[3] as SignupStep4PagerFragment).syndicateSignup()
                    return@setOnClickListener
                }
            }
        }

        binding.bSkip.setOnClickListener {
            navigate()
        }
    }

    private fun movePagertoNext() {
        binding.slvpSignup.setCurrentItem(binding.slvpSignup.currentItem + 1, true)
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

    fun sendOTP() {
        signupViewModel.sendOTP(SendOTPBody(phoneNumber = sharedPreferences.mobile_without_cc))
        signupViewModel.otp.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()

                        Toast.makeText(
                            this@SignupActivity,
                            getString(R.string.otp_sent),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                    }
                }
            }
        }
    }

    private fun checkOTP(otp: String) {
        signupViewModel.checkOTP(
            CheckOTPBody(
                otp = otp.toInt(),
                phoneNumber = sharedPreferences.mobile_without_cc
            )
        )
        signupViewModel.otpStatus.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        if (resource.data!!) {
                            sharedPreferences.isPhoneVerified = true
                            movePagertoNext()
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        Toast.makeText(this, getString(R.string.wrong_otp), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun getSyndicates() {
        signupViewModel.getSyndicates()
        signupViewModel.syndicates.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        SignupData.syndicatesList = resource.data!!.toMutableList()
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        getSyndicates()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (binding.slvpSignup.currentItem == 0)
            super.onBackPressed()
        else
            binding.slvpSignup.setCurrentItem(binding.slvpSignup.currentItem - 1, true)
    }
// endregion
}