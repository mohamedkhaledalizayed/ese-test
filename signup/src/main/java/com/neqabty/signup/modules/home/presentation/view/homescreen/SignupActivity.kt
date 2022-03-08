package com.neqabty.signup.modules.home.presentation.view.homescreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.signup.R
import com.neqabty.signup.core.ui.BaseActivity
import com.neqabty.signup.core.utils.Status
import com.neqabty.signup.databinding.ActivitySignupBinding
import com.neqabty.signup.modules.home.domain.entity.SignupParams
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
            .setMessage("من فضلك انتظر...")
            .build()

        if (sharedPreferences.mobile.isNotEmpty()){
            Toast.makeText(this, "Login", Toast.LENGTH_LONG).show()
        }

        signupViewModel.user.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!.nationalId.isNotEmpty()){
                            sharedPreferences.mobile = resource.data!!.mobile
                        }else{
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }

    fun signUp(view: View) {
        if (binding.membershipId.text.toString().isEmpty()){
            Toast.makeText(this, "من فضلك ادخل رقم العضوية", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.nationalId.text.toString().isEmpty()){
            Toast.makeText(this, "من فضلك ادخل الرقم القومى", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.nationalId.text.toString().length < 14){
            Toast.makeText(this, "من فضلك ادخل الرقم القومى صحيح", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.phone.text.toString().isEmpty()){
            Toast.makeText(this, "من فضلك ادخل رقم الموبايل", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.phone.text.toString().length < 11){
            Toast.makeText(this, "من فضلك ادخل رقم الموبايل صحيح", Toast.LENGTH_LONG).show()
            return
        }
        signupViewModel.signup(
            SignupParams(
                entityCode = "e0005",
                licenceNumber = "",
                membershipId = binding.membershipId.text.toString(),
                mobile = binding.phone.text.toString(),
                nationalId = binding.nationalId.text.toString(),
                password = ""
            )
        )
    }
}