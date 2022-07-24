package com.neqabty.healthcare.modules.verifyphone.view

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityVerifyPhoneBinding
import com.neqabty.healthcare.modules.home.presentation.view.homescreen.HomeActivity
import com.neqabty.healthcare.modules.verifyphone.data.model.CheckOTPBody
import com.neqabty.healthcare.modules.verifyphone.data.model.SendOTPBody
import com.neqabty.healthcare.modules.verifyphone.view.checkotp.CheckOTPFragment
import com.neqabty.healthcare.modules.verifyphone.view.sendotp.SendOTPFragment
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog


@Suppress("DEPRECATED_IDENTITY_EQUALS")
@AndroidEntryPoint
class VerifyPhoneActivity : BaseActivity<ActivityVerifyPhoneBinding>(), IVerifyPhoneListener {
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

        val fragment: Fragment = SendOTPFragment()

        val fm: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()


        verifyPhoneViewModel.otp.observe(this){
            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        loading.show()
                    }
                    Status.SUCCESS ->{
                        loading.hide()
                        openFragment()
                    }
                    Status.ERROR ->{
                        loading.hide()
                    }
                }
            }
        }
    }

    private fun openFragment() {
        val fragment = CheckOTPFragment.newInstance(phoneNumber)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )

        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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

    override fun onSendClicked(phone: String) {
        sharedPreferences.mobile = phone
        val intent = Intent(this@VerifyPhoneActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
//        checkForSmsReceivePermissions()
    }

    override fun onCheckClicked(otp: String) {
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