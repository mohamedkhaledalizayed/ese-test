package com.neqabty.valify.modules.home.presentation.view.homescreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.neqabty.valify.R
import com.neqabty.valify.modules.home.data.model.VerifyUserBody
import com.valify.valify_ekyc.sdk.Valify
import com.valify.valify_ekyc.sdk.ValifyConfig
import com.valify.valify_ekyc.sdk.ValifyFactory
import com.valify.valify_ekyc.viewmodel.ValifyData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var valifyClient: Valify
    private val homeViewModel: HomeViewModel by viewModels()
    var mobile = ""
    var userNumber = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        valifyClient = ValifyFactory(applicationContext).client

        mobile = intent.extras?.getString("mobile", "")!!
        userNumber = intent.extras?.getString("userNumber", "")!!
        findViewById<Button>(R.id.bStart).setOnClickListener{
            homeViewModel.getToken()
            observeOnValifyToken()
            observeOnVerifyUser()
        }
    }

    private fun observeOnVerifyUser() {
        homeViewModel.user.observe(this) {
            Toast.makeText(this, "تم تأكيد الهوية بنجاح", Toast.LENGTH_LONG).show()
            val bundle: Bundle = Bundle()
            bundle.putBoolean("isVerified", true)
            val intent = Intent()
            intent.putExtras(bundle)
            setResult(-1, intent)
            finish()
        }
    }

    private fun observeOnValifyToken() {
        homeViewModel.token.observe(this) {
            val valifyBuilder = ValifyConfig.Builder()
            valifyBuilder.setBaseUrl("https://valifystage.com/")
            valifyBuilder.setAccessToken(it)
            valifyBuilder.setBundleKey("13415658ea504635a05aaab8465e5005")
            valifyBuilder.setLanguage("ar")
            try {
                valifyClient.startActivityForResult(this, 1, valifyBuilder.build())
            } catch (e: Throwable) {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        valifyClient.handleActivityResult(resultCode, data, object : Valify.ValifyResultListener {
            override fun onSuccess(
                valifyToken: String, valifyData: ValifyData
            ) {
                Log.e("neqabty", "onSuccess")
                verifyUser(valifyData)
            }

            override fun onExit(
                valifyToken: String, step: String,
                valifyData: ValifyData
            ) {
                Log.e("neqabty", "onExit")
            }

            override fun onError(
                valifyToken: String, errorCode: Int,
                step: String, valifyData: ValifyData
            ) {
                Log.e("neqabty", "onError")
            }
        })
    }

    private fun verifyUser(valifyData: ValifyData) {
        val firstName = valifyData.ocrProcess.nationalIdData.firstName
        val fullName = valifyData.ocrProcess.nationalIdData.fullName
        val gender = valifyData.ocrProcess.nationalIdData.gender
        val address = valifyData.ocrProcess.nationalIdData.area
        homeViewModel.verifyUser(VerifyUserBody(mobile = mobile, user_number = userNumber, user_name = fullName, address = address))
    }
}