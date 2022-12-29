package com.neqabty.healthcare.auth.forgetpassword.view.changepassword

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.forgetpassword.data.model.ChangePasswordBody
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityChangePaawordBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class ChangePasswordActivity : BaseActivity<ActivityChangePaawordBinding>() {
    private lateinit var loading: AlertDialog
    private var token = ""
    private val changePasswordViewModel: ChangePasswordViewModel by viewModels()
    override fun getViewBinding() = ActivityChangePaawordBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        token = intent.getStringExtra("token")!!
        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        binding.password.loop(true)
        binding.changePassword.setOnClickListener {

            if (binding.newPassword.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    this@ChangePasswordActivity,
                    "من فضلك ادخل كلمة السر الجديدة.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            if (binding.confirmPassword.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    this@ChangePasswordActivity,
                    "من فضلك ادخل تاكيد كلمة السر الجديدة.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            if (binding.newPassword.text.toString() != binding.confirmPassword.text.toString()) {
                Toast.makeText(
                    this@ChangePasswordActivity,
                    "كلمة السر غير متطابقة.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            changePasswordViewModel.changePassword(
                ChangePasswordBody(
                    password = binding.newPassword.text.toString(),
                    passwordConfirmation = binding.confirmPassword.text.toString()
                ), "Token $token"
            )
        }

        changePasswordViewModel.status.observe(this) {
            it.let { resource ->
                when (resource.status) {

                    Status.LOADING -> {
                        loading.show()
                    }
                    Status.SUCCESS -> {
                        loading.dismiss()
                        if (resource.data!!.status){
                            finish()
                        }
                        Toast.makeText(this@ChangePasswordActivity, resource.data.message, Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        loading.dismiss()
                        Toast.makeText(
                            this@ChangePasswordActivity,
                            resource.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }
        }

    }
}