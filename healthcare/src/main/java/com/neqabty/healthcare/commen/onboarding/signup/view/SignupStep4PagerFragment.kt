package com.neqabty.healthcare.commen.onboarding.signup.view

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.healthcare.auth.signup.data.model.SignupBody
import com.neqabty.healthcare.commen.onboarding.signup.data.SignupData
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.utils.ErrorBody
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.FragmentSignupStepFourBinding

class SignupStep4PagerFragment : Fragment() {

    private lateinit var binding: FragmentSignupStepFourBinding
    lateinit var activity: SignupActivity
    private var isPasswordHidden = true
    private var isPasswordHiddenConfirm = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupStepFourBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = requireActivity() as SignupActivity
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser)
            initializeViews()
    }

    private fun initializeViews() {
        setAllViewsInvisible()
        binding.tvRegisterIn.text =
            getString(R.string.register_in).plus(" ").plus(SignupData.syndicateName)

        if (SignupData.syndicateID == Constants.NEQABTY_CODE) {
            binding.clName.visibility = View.VISIBLE
            binding.clEmail.visibility = View.VISIBLE
            binding.clPassword.visibility = View.VISIBLE
            binding.clPasswordConfirmation.visibility = View.VISIBLE
            binding.ivPasswordRules.visibility = View.VISIBLE
            binding.tvPasswordRules.visibility = View.VISIBLE
            binding.tvPasswordReset.visibility = View.VISIBLE
        }
        val entityValidationsList =
            SignupData.syndicatesList.findLast { it.code == SignupData.syndicateID }?.entityValidations

        if (entityValidationsList?.find { it.validationName == "has_membership_id" }?.value == true) {
            binding.clMembershipNumber.visibility = View.VISIBLE
        }

        if (entityValidationsList?.find { it.validationName == "has_national_id" }?.value == true) {
            binding.clNationalId.visibility = View.VISIBLE
        }

        binding.tvPasswordRules.setOnClickListener {
            Toast.makeText(activity, getString(R.string.password_conditions), Toast.LENGTH_LONG)
                .show()
        }

        binding.tvPasswordReset.setOnClickListener {
            binding.etPassword.setText("")
            binding.etPasswordConfirmation.setText("")
        }

        binding.ivPassword.setOnClickListener {
            showHidePassword(it)
        }

        binding.ivPasswordConfirmation.setOnClickListener {
            showHideConfirmPassword(it)
        }
    }

    private fun setAllViewsInvisible() {
        binding.clName.visibility = View.GONE
        binding.clMembershipNumber.visibility = View.GONE
        binding.clNationalId.visibility = View.GONE
        binding.clEmail.visibility = View.GONE
        binding.clPassword.visibility = View.GONE
        binding.clPasswordConfirmation.visibility = View.GONE
        binding.ivPasswordRules.visibility = View.GONE
        binding.tvPasswordRules.visibility = View.GONE
        binding.tvPasswordReset.visibility = View.GONE
    }

    fun syndicateSignup() {
        activity.signupViewModel.syndicateMemberSignup(
            SignupBody(
                entityCode = activity.sharedPreferences.code,
                nationalId = binding.etNationalId.text.toString(),
                mobile = activity.sharedPreferences.mobile,
                email = binding.tvEmail.text.toString(),
                membershipId = binding.etMembershipNumber.text.toString()
            )
        )

        activity.signupViewModel.syndicateMember.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        activity.showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        activity.hideProgressDialog()
                        if (resource.data!!.mobile.isNotEmpty()) {
                            activity.sharedPreferences.isSyndicateMember = true
                            activity.sharedPreferences.isAuthenticated = true
                            activity.sharedPreferences.token = resource.data.token.key
                            activity.sharedPreferences.email = binding.etEmail.text.toString()
                            activity.sharedPreferences.name = resource.data.fullname ?: ""
                            activity.sharedPreferences.nationalId = resource.data.nationalId ?: ""
                            activity.sharedPreferences.membershipId =
                                binding.etMembershipNumber.text.toString()
                            activity.sharedPreferences.code = resource.data.entity.code
                            activity.sharedPreferences.syndicateName = resource.data.entity.name
                            activity.sharedPreferences.image = resource.data.entity.imageUrl ?: ""

                            navigate()
                        } else {
                            Toast.makeText(
                                activity,
                                resources.getString(R.string.something_wrong),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    Status.ERROR -> {
                        activity.hideProgressDialog()
                        try {
                            val error =
                                Gson().fromJson(resource.message.toString(), ErrorBody::class.java)
                            Toast.makeText(activity, error.error, Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                activity,
                                resources.getString(R.string.something_wrong),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    fun generalSignup() {
        activity.signupViewModel.generalUserSignup(
            NeqabtySignupBody(
                email = binding.etEmail.text.toString(),
                fullname = binding.etName.text.toString(),
                mobile = activity.sharedPreferences.mobile,
                nationalId = binding.etNationalId.text.toString(),
                password = binding.etPassword.text.toString()
            )
        )
        activity.signupViewModel.generalUser.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        activity.showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        activity.hideProgressDialog()
                        if (resource.data!!.mobile.isNotEmpty()) {
                            activity.sharedPreferences.isAuthenticated = true
                            activity.sharedPreferences.isSyndicateMember = false
                            activity.sharedPreferences.token = resource.data!!.token
                            activity.sharedPreferences.name = resource.data!!.fullname ?: ""
                            activity.sharedPreferences.nationalId = resource.data!!.nationalId ?: ""
                            activity.sharedPreferences.code = resource.data!!.entityCode
                            activity.sharedPreferences.email = binding.etEmail.text.toString()
                            activity.sharedPreferences.syndicateName = resource.data!!.entityName
                            activity.sharedPreferences.image = resource.data!!.entityImage ?: ""
                            navigate()
                        } else {
                            Toast.makeText(
                                activity,
                                resources.getString(R.string.something_wrong),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    Status.ERROR -> {
                        activity.hideProgressDialog()
                        Toast.makeText(
                            activity,
                            resources.getString(R.string.something_wrong),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        }
    }

    private fun navigate() {
        val mainIntent = Intent(
            requireActivity(),
            SigninDoneActivity::class.java
        )
        startActivity(mainIntent)
        requireActivity().finish()
    }


    fun showHidePassword(view: View) {
        if (isPasswordHidden) {
            binding.etPassword.transformationMethod = null
            binding.ivPassword.setImageResource(R.drawable.ic_password_visible)
        } else {
            binding.etPassword.transformationMethod = PasswordTransformationMethod()
            binding.ivPassword.setImageResource(R.drawable.ic_password)
        }
        isPasswordHidden = !isPasswordHidden
    }

    fun showHideConfirmPassword(view: View) {
        if (isPasswordHiddenConfirm) {
            binding.etPasswordConfirmation.transformationMethod = null
            binding.ivPasswordConfirmation.setImageResource(R.drawable.ic_password_visible)
        } else {
            binding.etPasswordConfirmation.transformationMethod = PasswordTransformationMethod()
            binding.ivPasswordConfirmation.setImageResource(R.drawable.ic_password)
        }
        isPasswordHiddenConfirm = !isPasswordHiddenConfirm
    }
}