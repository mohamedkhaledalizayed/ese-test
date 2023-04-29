package com.neqabty.healthcare.auth.forgetpassword.view


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.forgetpassword.data.model.CheckOTPBody
import com.neqabty.healthcare.auth.forgetpassword.data.model.SendOTPBody
import com.neqabty.healthcare.auth.forgetpassword.view.changepassword.ChangePasswordActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityForgetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class ForgetPasswordActivity : BaseActivity<ActivityForgetPasswordBinding>(), IForgetPasswordListener {
    private lateinit var loading: AlertDialog
    private var phoneNumber = ""
    override fun getViewBinding() = ActivityForgetPasswordBinding.inflate(layoutInflater)
    private val forgetPasswordViewModel: ForgetPasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        phoneNumber = intent.getStringExtra("phone").toString()
        Log.e("phoneNumber", phoneNumber)
        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        val fragment: Fragment = SendFragment.newInstance(phoneNumber)

        val fm: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        forgetPasswordViewModel.otp.observe(this){
            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        loading.show()
                    }
                    Status.SUCCESS ->{
                        loading.hide()
                        if (resource.data!!.status){
                            openFragment()
                        }
                        Toast.makeText(this@ForgetPasswordActivity, resource.data.message, Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR ->{
                        loading.hide()
                        Toast.makeText(this@ForgetPasswordActivity, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }

    private fun openFragment() {
        val fragment = CheckFragment.newInstance(phoneNumber)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )

        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onSendClicked() {
        forgetPasswordViewModel.sendOTP(SendOTPBody(mobile = phoneNumber))
    }

    override fun onCheckClicked(otp: String) {
        forgetPasswordViewModel.checkOTP(CheckOTPBody(otp = otp.toInt()))

        forgetPasswordViewModel.otpStatus.observe(this){
            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        loading.show()
                    }
                    Status.SUCCESS ->{
                        loading.hide()
                        if (resource.data?.status!!){
                            val intent = Intent(this, ChangePasswordActivity::class.java)
                            intent.putExtra("token", resource.data.token)
                            startActivity(intent)
                            finish()
                        }
                        Toast.makeText(this, resource.data.message, Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR ->{
                        loading.hide()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}