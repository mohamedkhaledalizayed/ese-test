package com.neqabty.login.modules.login.presentation.view.homescreen

import android.app.AlertDialog
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.login.R
import com.neqabty.login.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

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
            .setMessage(resources.getString(R.string.please_wait))
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
                            sharedPreferences.isSyndicateMember = resource.data!!.user.account.entity.type == "syndicate"
                            sharedPreferences.token = resource.data!!.token
                            sharedPreferences.mobile = binding.etUsername.text.toString()
                            sharedPreferences.name = resource.data!!.user.account.fullName ?: ""
                            sharedPreferences.nationalId = resource.data!!.user.account.nationalId
                            sharedPreferences.userImage = "${resource.data!!.user.account.image}"
                            finish()
                        }else{
                            Toast.makeText(this, "حدث خطا", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
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
        hideKeyboard()
        if (binding.etPassword.text.toString().isEmpty()){
            Toast.makeText(this, resources.getString(R.string.enter_password), Toast.LENGTH_LONG).show()
            return
        }

        loginViewModel.login(binding.etUsername.text.toString(),
            binding.etPassword.text.toString())
    }
}