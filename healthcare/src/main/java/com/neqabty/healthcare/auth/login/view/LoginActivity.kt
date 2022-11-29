package com.neqabty.healthcare.auth.login.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityLoginBinding
import com.neqabty.healthcare.mega.home.view.MegaHomeActivity

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
                        if (resource.data!!.user.account.mobile.isNotEmpty()){
                            sharedPreferences.isPhoneVerified = resource.data!!.user.account.verifiedAccount
                            sharedPreferences.isSyndicateMember = resource.data!!.user.account.entity.type == "syndicate"
                            sharedPreferences.isAuthenticated = true
                            sharedPreferences.token = resource.data!!.token
                            sharedPreferences.email = resource.data!!.user.account.email
                            sharedPreferences.code = resource.data!!.user.account.entity.code
                            sharedPreferences.mainSyndicate = resource.data!!.user.account.entity.id
                            sharedPreferences.image = resource.data!!.user.account.entity.image ?: ""
                            sharedPreferences.syndicateName = resource.data!!.user.account.entity.name
                            sharedPreferences.mobile = binding.etUsername.text.toString()
                            sharedPreferences.name = resource.data!!.user.account.fullName ?: ""
                            sharedPreferences.nationalId = resource.data!!.user.account.nationalId ?: ""
                            sharedPreferences.membershipId = resource.data!!.user.membershipId
                            sharedPreferences.userImage = "${resource.data!!.user.account.image}"
                                if (resource.data!!.user.account.entity.type == "syndicate"){
                                    val mainIntent = Intent(this@LoginActivity, MegaHomeActivity::class.java)
                                    startActivity(mainIntent)
                                    finish()
                                }else{
                                    val mainIntent = Intent(this@LoginActivity, com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.SehaHomeActivity::class.java)
                                    startActivity(mainIntent)
                                    finish()
                                }

                        }else{
                            Toast.makeText(this, resources.getString(R.string.something_wrong), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        binding.etUsername.customSelectionActionModeCallback = actionMode
        binding.etPassword.customSelectionActionModeCallback = actionMode
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