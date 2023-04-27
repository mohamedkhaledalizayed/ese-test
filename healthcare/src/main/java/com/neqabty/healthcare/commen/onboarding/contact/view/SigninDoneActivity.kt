package com.neqabty.healthcare.commen.onboarding.contact.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.otp.data.model.SendOTPBody
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySigninDoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninDoneActivity : BaseActivity<ActivitySigninDoneBinding>() {
    override fun getViewBinding() = ActivitySigninDoneBinding.inflate(layoutInflater)
    val signinDoneViewModel: SigninDoneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.signin_done)
        initializeViews()
    }

    private fun initializeViews() {
        if (sharedPreferences.nationalId.isNotEmpty()) {
            binding.etNationalId.setText(sharedPreferences.nationalId)
            binding.etNationalId.isEnabled = false
        }

        binding.bHome.setOnClickListener {
            navigate()
        }

        binding.bNext.setOnClickListener {
            sharedPreferences.nationalId = "29701011422117"

            signinDoneViewModel.checkMemberStatus(nationalId = binding.etNationalId.text.toString())
            signinDoneViewModel.checkMemberStatus.observe(this) {
                it.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            showProgressDialog()
                        }
                        Status.SUCCESS -> {
                            hideProgressDialog()

                            Toast.makeText(
                                this@SigninDoneActivity,
                                getString(R.string.otp_sent),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        Status.ERROR -> {
                            hideProgressDialog()
                            showTermsDialog()
                        }
                    }
                }
            }
        }
    }

    private fun showTermsDialog() {
        val bottomSheetFragment = ContactTermsBottomSheet()
        bottomSheetFragment.onActionListener = object: ContactTermsBottomSheet.OnActionListener {
            override fun onAcceptListener() {
                val mainIntent = Intent(
                    this@SigninDoneActivity,
                    UploadIdFrontActivity::class.java
                )
                startActivity(mainIntent)
                finish()
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
            getTheNextActivityFromSignup()
        )
        startActivity(mainIntent)
        finish()
    }
// endregion
}