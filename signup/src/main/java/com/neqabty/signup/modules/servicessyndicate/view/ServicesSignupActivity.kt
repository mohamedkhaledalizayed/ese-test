package com.neqabty.signup.modules.servicessyndicate.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.isDigitsOnly
import com.google.gson.Gson
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.ErrorBody
import com.neqabty.core.utils.Status
import com.neqabty.core.utils.isValidEmail
import com.neqabty.signup.R
import com.neqabty.signup.databinding.ActivityServicesSignupBinding
import com.neqabty.signup.modules.servicessyndicate.domain.entity.SignupParams
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class ServicesSignupActivity : BaseActivity<ActivityServicesSignupBinding>() {

    private lateinit var dialog: AlertDialog
    private val signupViewModel: SignupViewModel by viewModels()
    override fun getViewBinding() = ActivityServicesSignupBinding.inflate(layoutInflater)
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

        signupViewModel.user.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!.nationalId.isNotEmpty()){
                            sharedPreferences.isSyndicateMember = true
                            sharedPreferences.isAuthenticated = true
                            sharedPreferences.mobile = binding.phone.text.toString()
                            sharedPreferences.token = resource.data!!.token.key
                            sharedPreferences.email = binding.email.text.toString()
                            sharedPreferences.name = resource.data!!.fullname ?: ""
                            sharedPreferences.nationalId = resource.data!!.nationalId
                            sharedPreferences.code = resource.data!!.entity.code
                            sharedPreferences.syndicateName = resource.data!!.entity.name
                            sharedPreferences.image = resource.data!!.entity.imageUrl ?: ""
                            confirmMessage(resources.getString(R.string.confirm_message))
                        }else{
                            Toast.makeText(this, resources.getString(R.string.something_wrong), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                        val error = Gson().fromJson(resource.message.toString(), ErrorBody::class.java)

                        Toast.makeText(this, error.error, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

    }

    private fun confirmMessage(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(resources.getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.ok_btn)
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        alertDialog.show()

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

        if (binding.nationalId.text.toString().isEmpty() || !binding.nationalId.text.isDigitsOnly() || binding.nationalId.text.toString().length < 14){
            Toast.makeText(this, resources.getString(R.string.enter_national_id), Toast.LENGTH_LONG).show()
            return
        }


            signupViewModel.signup(
                SignupParams(
                    entityCode = "e09",
                    mobile = binding.phone.text.toString(),
                    national_id = binding.nationalId.text.toString(),
                    email = binding.email.text.toString()
                )
            )

    }
}