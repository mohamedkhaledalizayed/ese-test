package com.neqabty.healthcare.commen.checkaccountstatus.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.auth.login.view.LoginActivity
import com.neqabty.healthcare.databinding.ActivityCheckAccountBinding
import com.neqabty.healthcare.commen.checkaccountstatus.data.model.CheckPhoneBody
import com.neqabty.healthcare.commen.syndicates.presentation.view.homescreen.SyndicateActivity
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
                            finish()
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
}