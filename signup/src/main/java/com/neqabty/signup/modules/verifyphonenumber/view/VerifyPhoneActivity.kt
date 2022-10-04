package com.neqabty.signup.modules.verifyphonenumber.view

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.signup.R
import com.neqabty.signup.databinding.ActivityVerifyPhoneBinding
import com.neqabty.signup.modules.verifyphonenumber.data.model.CheckOTPBody
import com.neqabty.signup.modules.verifyphonenumber.data.model.SendOTPBody
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

        val fragment: Fragment = SendOTPFragment.newInstance(sharedPreferences.mobile)

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
        phoneNumber = phone
        verifyPhoneViewModel.sendOTP(SendOTPBody(phoneNumber = phoneNumber))
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
                            phoneVerified()
                        }
                    }
                    Status.ERROR ->{
                        loading.hide()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun phoneVerified() {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(getString(R.string.phone_confirmed))
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.ok_btn)
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        alertDialog.show()

    }

    override fun onBackPressed() {
        finish()
    }
}