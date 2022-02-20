package com.neqabty.signup.modules.home.presentation.view.homescreen

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
import javax.inject.Inject

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding>() {

    private val signupViewModel: SignupViewModel by viewModels()

    override fun getViewBinding() = ActivitySignupBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupToolbar(titleResId = R.string.signup)

        if (sharedPreferences.mobile.isNotEmpty()){
            Toast.makeText(this, "Login", Toast.LENGTH_LONG).show()
        }

        signupViewModel.user.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        if (resource.data!!.nationalId.isNotEmpty()){
                            sharedPreferences.mobile = resource.data!!.mobile
                        }else{
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
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

        if (binding.licenceNumber.text.toString().isEmpty()){
            Toast.makeText(this, "من فضلك ادخل رقم الترخيص", Toast.LENGTH_LONG).show()
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

        if (binding.password.text.toString().isEmpty()){
            Toast.makeText(this, "من فضلك ادخل كلمة المرور", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.password.text.toString().length < 6){
            Toast.makeText(this, "من فضلك كلمة المرور لا تقل عن 6 ارقام و حروف", Toast.LENGTH_LONG).show()
            return
        }
        signupViewModel.signup(
            SignupParams(
                entityCode = "e0005",
                licenceNumber = binding.licenceNumber.text.toString(),
                membershipId = binding.membershipId.text.toString(),
                mobile = binding.phone.text.toString(),
                nationalId = binding.nationalId.text.toString(),
                password = binding.password.text.toString()
            )
        )
    }
}