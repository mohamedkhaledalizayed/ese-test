package com.neqabty.healthcare.commen.contact_invoice.view

import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityContactInvoiceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactInvoiceActivity : BaseActivity<ActivityContactInvoiceBinding>() {
    private val contactInvoiceViewModel: ContactInvoiceViewModel by viewModels()

    override fun getViewBinding() = ActivityContactInvoiceBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.contact_installments)

        observeOnInvoiceStatus()
        val tenor = intent.getStringExtra("tenor")
        val maxTenor = intent.getStringExtra("maxTenor")
        val instalmentValue = intent.getStringExtra("instalmentValue")
        val amount = intent.getStringExtra("amount")

        binding.tvMonthlyInstallment.text = "${getString(R.string.contact_monthly_installment)} $instalmentValue"
        binding.tvMonths.text = "${getString(R.string.contact_months)} $tenor"
        binding.bSubmit.setOnClickListener {
            contactInvoiceViewModel.submitInvoice(sharedPreferences.nationalId, amount!!, tenor!!)
        }
    }

    private fun observeOnInvoiceStatus() {
        contactInvoiceViewModel.invoice.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        if (resource.data != null) {
//                            mAdapter.submitList(resource.data?.toMutableList())
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        showAlert(message = resource.message ?: "") { }
                    }
                }
            }
        }
    }

    //region
// endregion
}