package com.neqabty.healthcare.modules.checkaccountstatus.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.hbb20.CountryCodePicker
import com.neqabty.core.ui.BaseActivity
import com.neqabty.healthcare.R
import com.neqabty.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityCheckAccountBinding
import com.neqabty.healthcare.modules.checkaccountstatus.data.model.CheckPhoneBody
import com.neqabty.core.utils.isMobileValid
import com.neqabty.healthcare.modules.home.presentation.view.homescreen.HomeActivity
import com.neqabty.login.modules.login.presentation.view.homescreen.LoginActivity
import com.neqabty.healthcare.modules.syndicates.presentation.view.homescreen.SyndicateActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog


@Suppress("DEPRECATED_IDENTITY_EQUALS")
@AndroidEntryPoint
class CheckAccountActivity : BaseActivity<ActivityCheckAccountBinding>() {
    override fun getViewBinding() = ActivityCheckAccountBinding.inflate(layoutInflater)
    private lateinit var loading: AlertDialog
    private val checkAccountViewModel: CheckAccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        binding.ccp.registerCarrierNumberEditText(binding.phone)
        binding.btnSend.setOnClickListener {
            if (binding.phone.text.isNullOrEmpty()){
                Toast.makeText(this, getString(R.string.enter_phone), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
//            if (!binding.phone.text.toString().isMobileValid()){
//                Toast.makeText(this, getString(R.string.enter_corect_phone), Toast.LENGTH_LONG).show()
//                return@setOnClickListener
//            }

            if (binding.ccp.isValidFullNumber) {
                checkAccountViewModel.checkAccount(CheckPhoneBody(mobile = binding.ccp.fullNumberWithPlus))
            }else{
                Toast.makeText(this, "Not Valid Number", Toast.LENGTH_LONG).show()
            }

            Log.e("test", binding.ccp.fullNumberWithPlus)


        }


        checkAccountViewModel.accountStatus.observe(this){
            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        loading.show()
                    }
                    Status.SUCCESS ->{
                        loading.hide()
                        if (resource.data == "Found"){
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.putExtra("phone", binding.ccp.fullNumberWithPlus)
                            startActivity(intent)
                        }else{
                            sharedPreferences.mobile = binding.ccp.fullNumberWithPlus
                            val intent = Intent(this, SyndicateActivity::class.java)
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

    override fun onResume() {
        super.onResume()
        if (sharedPreferences.mobile.isNotEmpty()){
            if (sharedPreferences.isSyndicateMember){
                val mainIntent = Intent(this@CheckAccountActivity, com.neqabty.meganeqabty.home.view.homescreen.HomeActivity::class.java)
                startActivity(mainIntent)
                finish()
            }else{
                val mainIntent = Intent(this@CheckAccountActivity, HomeActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}