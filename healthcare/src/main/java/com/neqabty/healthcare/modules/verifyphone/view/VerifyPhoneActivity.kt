package com.neqabty.healthcare.modules.verifyphone.view

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityVerifyPhoneBinding
import com.neqabty.healthcare.modules.home.presentation.view.homescreen.HomeActivity
import com.neqabty.healthcare.modules.verifyphone.data.model.CheckOTPBody
import com.neqabty.healthcare.modules.verifyphone.data.model.CheckPhoneBody
import com.neqabty.healthcare.modules.verifyphone.data.model.SendOTPBody
import com.neqabty.login.modules.login.presentation.view.homescreen.LoginActivity
import com.neqabty.signup.core.utils.isMobileValid
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog


@Suppress("DEPRECATED_IDENTITY_EQUALS")
@AndroidEntryPoint
class VerifyPhoneActivity : BaseActivity<ActivityVerifyPhoneBinding>() {
    override fun getViewBinding() = ActivityVerifyPhoneBinding.inflate(layoutInflater)
    private lateinit var loading: AlertDialog
    private var phoneNumber = ""
    private val verifyPhoneViewModel: VerifyPhoneViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()


        binding.btnSend.setOnClickListener {
            if (binding.phone.text.isNullOrEmpty()){
                Toast.makeText(this, "من فضلك ادخل رقم الهاتف اولا.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!binding.phone.text.toString().isMobileValid()){
                Toast.makeText(this, "من فضلك ادخل رقم صحيح.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            verifyPhoneViewModel.checkAccount(CheckPhoneBody(mobile = binding.phone.text.toString()))

        }

        verifyPhoneViewModel.accountStatus.observe(this){
            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        loading.show()
                    }
                    Status.SUCCESS ->{
                        loading.hide()
                        if (resource.data == "Found"){
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.putExtra("phone", binding.phone.text.toString())
                            startActivity(intent)
                        }
                    }
                    Status.ERROR ->{
                        loading.hide()
                        if (resource.message.toString() == "404"){
                            sharedPreferences.mobile = binding.phone.text.toString()
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (sharedPreferences.isPhoneVerified){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    private fun checkForSmsReceivePermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                "android.permission.RECEIVE_SMS"
            ) === PackageManager.PERMISSION_GRANTED
        ) {
            verifyPhoneViewModel.sendOTP(SendOTPBody(phoneNumber = phoneNumber))
        } else {
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.RECEIVE_SMS), 43391)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 43391){
            verifyPhoneViewModel.sendOTP(SendOTPBody(phoneNumber = phoneNumber))
        }
    }


    fun onCheckClicked(otp: String) {
        verifyPhoneViewModel.checkOTP(CheckOTPBody(otp = otp.toInt(), phoneNumber = phoneNumber))


        verifyPhoneViewModel.otpStatus.observe(this){
            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        loading.show()
                    }
                    Status.SUCCESS ->{
                        loading.hide()
                        if (resource.data!!){
                            sharedPreferences.isPhoneVerified = true
                            sharedPreferences.phoneVerified = phoneNumber
                            val intent = Intent(this@VerifyPhoneActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    Status.ERROR ->{
                        loading.hide()
                    }
                }
            }
        }
    }
}