package com.neqabty.meganeqabty.payment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.login.R
import com.neqabty.login.modules.login.presentation.view.homescreen.LoginActivity
import com.neqabty.meganeqabty.core.ui.BaseActivity
import com.neqabty.meganeqabty.core.utils.Status
import com.neqabty.meganeqabty.databinding.ActivityPaymentsBinding
import com.neqabty.meganeqabty.paymentdetails.view.paymentdetails.PaymentDetailsActivity
import com.neqabty.meganeqabty.syndicates.domain.entity.ServiceEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsActivity : BaseActivity<ActivityPaymentsBinding>() {

    private var listOfServices: ArrayList<ServiceEntity> = ArrayList()
    private var serviceId = ""
    private var requireRegistration = false
    private lateinit var mAdapter: CustomAdapter
    private val paymentViewModel: PaymentViewModel by viewModels()
    override fun getViewBinding() = ActivityPaymentsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        val intent = Intent(this, PaymentStatusActivity::class.java)
//        intent.putExtra("referenceCode", "76fc1e7a-89ee-46e5-b1d7-64acd4a97d49")
//        startActivity(intent)
        setupToolbar(titleResId = R.string.payments)
        paymentViewModel.getSyndicates()
        paymentViewModel.syndicates.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        val result = it.data?.filter { s -> s.code == sharedPreferences.code }
//                        listOfServices.addAll(result!![0].services)
//                        listOfServices.add(0, ServiceEntity(name = "إختر الخدمة"))

                        mAdapter = CustomAdapter(this, listOfServices.toMutableList())
                        binding.spServices.adapter = mAdapter
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }


        }

        binding.btnNext.setOnClickListener {
            submit()
        }

        binding.spServices.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (listOfServices.size != 0) {
                    serviceId = listOfServices[i].code
                    requireRegistration = listOfServices[i].requireRegistration
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun submit() {
        if (binding.membershipId.text.isEmpty()){
            Toast.makeText(this, getString(com.neqabty.meganeqabty.R.string.enter_mermber_ship_id), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.spServices.selectedItemPosition == 0){
            Toast.makeText(this, getString(com.neqabty.meganeqabty.R.string.select_service), Toast.LENGTH_LONG).show()
            return
        }

        if (requireRegistration){
            if (sharedPreferences.mobile.isNotEmpty()){
                paymentDetails()
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }else{
            paymentDetails()
        }
    }

    private fun paymentDetails(){
        val intent = Intent(this, PaymentDetailsActivity::class.java)
        intent.putExtra("code", serviceId)
        intent.putExtra("number", binding.membershipId.text.toString())
        startActivity(intent)
    }
}