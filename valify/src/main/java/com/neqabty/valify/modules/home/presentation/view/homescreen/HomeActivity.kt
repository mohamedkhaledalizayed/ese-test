package com.neqabty.valify.modules.home.presentation.view.homescreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import com.neqabty.valify.R
import com.neqabty.valify.modules.home.data.model.VerifyUserBody
import com.valify.valify_ekyc.sdk.Valify
import com.valify.valify_ekyc.sdk.ValifyConfig
import com.valify.valify_ekyc.sdk.ValifyFactory
import com.valify.valify_ekyc.viewmodel.ValifyData
import dagger.hilt.android.AndroidEntryPoint
import org.spongycastle.i18n.filter.HTMLFilter

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
            findViewById<ProgressBar>(R.id.superProgressbar).visibility = View.GONE
            Toast.makeText(this, getString(R.string.success), Toast.LENGTH_LONG).show()
            val bundle: Bundle = Bundle()
            bundle.putBoolean("isVerified", true)
            val intent = Intent()
            intent.putExtras(bundle)
            setResult(-1, intent)
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
                findViewById<ProgressBar>(R.id.superProgressbar).visibility = View.VISIBLE
                verifyUser(valifyData)
            }

            override fun onExit(
                valifyToken: String, step: String,
                valifyData: ValifyData
            ) {
                Toast.makeText(this@HomeActivity, getString(R.string.error), Toast.LENGTH_LONG).show()
            }

            override fun onError(
                valifyToken: String, errorCode: Int,
                step: String, valifyData: ValifyData
            ) {
                Toast.makeText(this@HomeActivity, getString(R.string.error), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun verifyUser(valifyData: ValifyData) {
        val firstName = valifyData.ocrProcess.nationalIdData.firstName
        val fullName = valifyData.ocrProcess.nationalIdData.fullName
        val gender = valifyData.ocrProcess.nationalIdData.gender
        val job = valifyData.ocrProcess.nationalIdData.profession
        val address = valifyData.ocrProcess.nationalIdData.area
        val nationalID = valifyData.ocrProcess.nationalIdData.frontNid

        findViewById<LinearLayout>(R.id.llStart).visibility = View.GONE
        findViewById<LinearLayout>(R.id.llFinish).visibility = View.VISIBLE

        findViewById<TextView>(R.id.tvName).text = HtmlCompat.fromHtml(getString(R.string.name_title, "$firstName $fullName"), FROM_HTML_MODE_COMPACT)
//        findViewById<TextView>(R.id.tvNationalId).text = HtmlCompat.fromHtml(getString(R.string.national_id_title, nationalID), FROM_HTML_MODE_COMPACT)
//        findViewById<TextView>(R.id.tvAddress).text = HtmlCompat.fromHtml(getString(R.string.address_title, address), FROM_HTML_MODE_COMPACT)
        findViewById<TextView>(R.id.tvJob).text = HtmlCompat.fromHtml(getString(R.string.job_title, job), FROM_HTML_MODE_COMPACT)

        homeViewModel.verifyUser(VerifyUserBody(mobile = mobile, user_number = userNumber, user_name = "$firstName $fullName", address = address, nationalID = nationalID))
    }
}