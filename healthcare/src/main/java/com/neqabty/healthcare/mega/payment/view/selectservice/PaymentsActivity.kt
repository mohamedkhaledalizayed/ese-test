package com.neqabty.healthcare.mega.payment.view.selectservice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.ActivityPaymentsBinding
import com.neqabty.healthcare.mega.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.healthcare.mega.payment.domain.entity.services.ServicesListEntity
import com.neqabty.healthcare.mega.payment.view.PaymentViewModel
import com.neqabty.healthcare.mega.payment.view.paymentdetails.PaymentDetailsActivity
import com.neqabty.healthcare.mega.payment.view.paymentstatus.PaymentStatusActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsActivity : BaseActivity<ActivityPaymentsBinding>() {

    private var listOfServices: ArrayList<ServicesListEntity> = ArrayList()
    private var listOfActions: ArrayList<ServiceActionsEntity> = ArrayList()
    private var serviceId = ""
    private var actionId = ""
    private lateinit var mServicesAdapter: ServicesAdapter
    private lateinit var mActionsAdapter: ServiceActionsAdapter
    private val paymentViewModel: PaymentViewModel by viewModels()
    override fun getViewBinding() = ActivityPaymentsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        val intent = Intent(this, PaymentStatusActivity::class.java)
//        intent.putExtra("referenceCode", "ffb82ec9-07e6-4761-ab78-7714f9a646a3")
//        startActivity(intent)
        setupToolbar(titleResId = R.string.payments)

        if (sharedPreferences.membershipId.isEmpty()){
            binding.membershipIdContainer.visibility = View.GONE
        }else{
            binding.membershipId.setText(sharedPreferences.membershipId)
            binding.membershipId.isEnabled = false
        }

        paymentViewModel.getServices()
        paymentViewModel.services.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        listOfServices.addAll(resource.data!!)
                        listOfServices.add(0, ServicesListEntity(name = resources.getString(R.string.sel_service)))

                        mServicesAdapter = ServicesAdapter(this, listOfServices.toMutableList())
                        binding.spServices.adapter = mServicesAdapter

                        val item = mServicesAdapter.syndicatesList.find { it.code == intent.extras?.getString("id") }
                        val position = mServicesAdapter.syndicatesList.indexOf(item)
                        binding.spServices.setSelection(position)
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
                        listOfActions.add(0, ServiceActionsEntity(name = resources.getString(R.string.sel_service)))

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
                    paymentViewModel.getServiceActions(listOfServices[i].code)
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.spActions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (listOfActions.size != 0) {
                    actionId = listOfActions[i].code
                }else{
                    Toast.makeText(this@PaymentsActivity, resources.getString(R.string.sel_service), Toast.LENGTH_LONG).show()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun submit() {
//        if (binding.membershipId.text.isEmpty()){
//            Toast.makeText(this, getString(R.string.enter_mermber_ship_id), Toast.LENGTH_LONG).show()
//            return
//        }

        if (binding.spServices.selectedItemPosition == 0){
            Toast.makeText(this, "من فضلك اختر الخدمة", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.spActions.selectedItemPosition == 0){
            Toast.makeText(this, "من فضلك اختر نوع الخدمة", Toast.LENGTH_LONG).show()
            return
        }

        paymentDetails()
    }

    private fun paymentDetails(){
        val intent = Intent(this, PaymentDetailsActivity::class.java)
        intent.putExtra("code", serviceId)
        intent.putExtra("service_action_code", actionId)
        startActivity(intent)
    }
}