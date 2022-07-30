package com.neqabty.meganeqabty.payment.view.selectservice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.login.R
import com.neqabty.meganeqabty.core.ui.BaseActivity
import com.neqabty.meganeqabty.core.utils.Status
import com.neqabty.meganeqabty.databinding.ActivityPaymentsBinding
import com.neqabty.meganeqabty.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.meganeqabty.payment.domain.entity.services.ServicesListEntity
import com.neqabty.meganeqabty.payment.view.PaymentViewModel
import com.neqabty.meganeqabty.payment.view.paymentdetails.PaymentDetailsActivity
import com.neqabty.signup.modules.verifyphonenumber.view.VerifyPhoneActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsActivity : BaseActivity<ActivityPaymentsBinding>() {

    private var listOfServices: ArrayList<ServicesListEntity> = ArrayList()
    private var listOfActions: ArrayList<ServiceActionsEntity> = ArrayList()
    private var serviceId = ""
    private var actionId = ""
    private var requireRegistration = false
    private lateinit var mServicesAdapter: ServicesAdapter
    private lateinit var mActionsAdapter: ServiceActionsAdapter
    private val paymentViewModel: PaymentViewModel by viewModels()
    override fun getViewBinding() = ActivityPaymentsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        val intent = Intent(this, PaymentStatusActivity::class.java)
//        intent.putExtra("referenceCode", "d1d9bf1e-8963-4f01-8692-b928e213e24d")
//        startActivity(intent)
        setupToolbar(titleResId = R.string.payments)
        paymentViewModel.getServices()
        paymentViewModel.services.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        listOfServices.addAll(resource.data!!)
                        listOfServices.add(0, ServicesListEntity(name = "إختر الخدمة"))

                        mServicesAdapter = ServicesAdapter(this, listOfServices.toMutableList())
                        binding.spServices.adapter = mServicesAdapter
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }


        }

        paymentViewModel.serviceActions.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        listOfActions.clear()
                        binding.spinnerActionContainer.visibility = View.VISIBLE
                        listOfActions.addAll(resource.data!!)
                        listOfActions.add(0, ServiceActionsEntity(name = "إختر الخدمة"))

                        mActionsAdapter = ServiceActionsAdapter(this, listOfActions.toMutableList())
                        binding.spActions.adapter = mActionsAdapter
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
                if (i != 0) {
                    serviceId = listOfServices[i].code
//                    requireRegistration = listOfServices[i].requireRegistration
                    paymentViewModel.getServiceActions(listOfServices[i].code)
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.spActions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (listOfActions.size != 0) {
                    actionId = listOfActions[i].code
//                    requireRegistration = listOfActions[i].requireRegistration
                }else{
                    Toast.makeText(this@PaymentsActivity, "من فضلك إختر الخدمة اولا.", Toast.LENGTH_LONG).show()
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
            if (sharedPreferences.isPhoneVerified){
                paymentDetails()
            }else{
                val intent = Intent(this, VerifyPhoneActivity::class.java)
                startActivity(intent)
            }
        }else{
            paymentDetails()
        }
    }

    private fun paymentDetails(){
        val intent = Intent(this, PaymentDetailsActivity::class.java)
        intent.putExtra("code", serviceId)
        intent.putExtra("service_action_code", actionId)
        intent.putExtra("number", binding.membershipId.text.toString())
        startActivity(intent)
    }
}