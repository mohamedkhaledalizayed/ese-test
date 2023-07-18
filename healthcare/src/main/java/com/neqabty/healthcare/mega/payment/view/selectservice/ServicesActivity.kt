package com.neqabty.healthcare.mega.payment.view.selectservice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityServicesBinding
import com.neqabty.healthcare.mega.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.healthcare.mega.payment.view.PaymentViewModel
import com.neqabty.healthcare.mega.payment.view.paymentdetails.PaymentDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServicesActivity : BaseActivity<ActivityServicesBinding>() {


    private var serviceId: String = ""
    private lateinit var listOfServices: List<ServiceActionsEntity>
    private lateinit var mAdapter: ServiceActionsAdapter
    private val paymentViewModel: PaymentViewModel by viewModels()
    override fun getViewBinding() = ActivityServicesBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        serviceId = intent.getStringExtra("id") ?: ""

        setupToolbar(title = intent.getStringExtra("name") ?: "")

        paymentViewModel.getServiceActions(serviceId)
        paymentViewModel.serviceActions.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        listOfServices = resource.data!!
                        mAdapter = ServiceActionsAdapter(this, resource.data.toMutableList())
                        binding.serviceActions.adapter = mAdapter
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        binding.serviceActions.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this@ServicesActivity, PaymentDetailsActivity::class.java)
            intent.putExtra("code", serviceId)
            intent.putExtra("service_action_code", listOfServices[i].code)
            startActivity(intent)
        }
    }

}