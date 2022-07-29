package com.neqabty.login.modules.login.presentation.view.homescreen

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.neqabty.login.R
import com.neqabty.login.core.ui.BaseActivity
import com.neqabty.login.core.utils.ParcelClickListenerExtra
import com.neqabty.login.core.utils.Status
import com.neqabty.login.core.utils.isMobileValid
import com.neqabty.login.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private lateinit var dialog: AlertDialog
    private val loginViewModel: LoginViewModel by viewModels()
    private var isHidden = true

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupToolbar(titleResId = R.string.login, showUp = false)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("من فضلك انتظر...")
            .build()


        binding.etUsername.setText(intent.getStringExtra("phone").toString())
        binding.etUsername.isEnabled = false
        loginViewModel.user.observe(this)  {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!.user.account.nationalId.isNotEmpty()){
                            sharedPreferences.isPhoneVerified = true
                            //TODO We need to check your type here
                            sharedPreferences.token = resource.data.token
                            sharedPreferences.mobile = binding.etUsername.text.toString()
                            sharedPreferences.name = resource.data.user.account.fullName ?: ""
                            sharedPreferences.nationalId = resource.data.user.account.nationalId
                            sharedPreferences.userImage = "${resource.data.user.account.image}"
                            finish()
                        }else{
                            Toast.makeText(this, "حدث خطا", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                        Log.e("test", resource.message.toString())
                    }
                }
            }

        }
    }

    fun showHidePassword(view: View) {
        if (isHidden) {
            isHidden = false
            binding.etPassword.transformationMethod = null
            binding.showHide.setImageResource(R.drawable.ic_baseline_visibility_24)
        } else {
            isHidden = true
            binding.etPassword.transformationMethod = PasswordTransformationMethod()
            binding.showHide.setImageResource(R.drawable.ic_baseline_visibility_off_24)
        }
    }

    fun login(view: View) {

        if (binding.etPassword.text.toString().isEmpty()){
            Toast.makeText(this, "من فضلك ادخل كلمة المرور", Toast.LENGTH_LONG).show()
            return
        }

        loginViewModel.login(binding.etUsername.text.toString(),
            binding.etPassword.text.toString())
    }
}