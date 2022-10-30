package com.neqabty.shealth.auth.signup.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.shealth.R
import com.neqabty.shealth.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.shealth.core.ui.BaseActivity
import com.neqabty.shealth.core.utils.Status
import com.neqabty.shealth.core.utils.isValidEmail
import com.neqabty.shealth.databinding.ActivitySignupBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding>() {
    private lateinit var dialog: AlertDialog
    private val signupViewModel: SignupViewModel by viewModels()
    override fun getViewBinding() = ActivitySignupBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupToolbar(titleResId = R.string.signup)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(resources.getString(R.string.please_wait))
            .build()

        binding.phone.setText(sharedPreferences.mobile)
        binding.phone.isEnabled = false


        signupViewModel.neqabtyMember.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!.mobile.isNotEmpty()){
                            sharedPreferences.isAuthenticated = true
                            sharedPreferences.token = resource.data.token
                            sharedPreferences.mobile = binding.phone.text.toString()
                            sharedPreferences.name = resource.data.fullname ?: ""
                            sharedPreferences.nationalId = resource.data.nationalId ?: ""
                            sharedPreferences.code = resource.data.entityCode
                            sharedPreferences.email = binding.email.text.toString()
                            sharedPreferences.syndicateName = resource.data.entityName
                            sharedPreferences.image = resource.data.entityImage ?: ""
                            finish()
                        }else{
                            Toast.makeText(this, resources.getString(R.string.something_wrong), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }

        }
    }


    fun signUp(view: View) {

        if (binding.phone.text.toString().isEmpty()){
            Toast.makeText(this, resources.getString(R.string.enter_phone), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.email.text.toString().isNullOrEmpty()){
            Toast.makeText(this, resources.getString(R.string.enter_email), Toast.LENGTH_LONG).show()
            return
        }

        if (!binding.email.text.toString().isValidEmail()){
            Toast.makeText(this, resources.getString(R.string.enter_correct_email), Toast.LENGTH_LONG).show()
            return
        }



            if (binding.fullName.text.toString().isEmpty()){
                Toast.makeText(this, resources.getString(R.string.enter_name), Toast.LENGTH_LONG).show()
                return
            }

            if (binding.password.text.toString().isEmpty()){
                Toast.makeText(this, resources.getString(R.string.enter_password), Toast.LENGTH_LONG).show()
                return
            }

            signupViewModel.signupNeqabtyMember(
                NeqabtySignupBody(
                    email = binding.email.text.toString(),
                    fullname = binding.fullName.text.toString(),
                    mobile = binding.phone.text.toString(),
                    nationalId = binding.nationalId.text.toString(),
                    password = binding.password.text.toString()
                )
            )

    }

}