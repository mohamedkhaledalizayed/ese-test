package com.neqabty.healthcare.contact.contact_installments.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.contact.contact_invoice.view.ContactInvoiceActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityContactInstallmentsBinding
import com.neqabty.healthcare.medicalnetwork.domain.entity.search.ProvidersEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactInstallmentsActivity : BaseActivity<ActivityContactInstallmentsBinding>() {
    private val contactInstallmentsViewModel: ContactInstallmentsViewModel by viewModels()

    override fun getViewBinding() = ActivityContactInstallmentsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.contact_installments)

        val provider = intent.getParcelableExtra<ProvidersEntity>("provider")
        observeOnInstallmentsStatus()
        initializeViews()
    }

    private fun initializeViews() {
        binding.bCalculate.setOnClickListener {
            if(binding.etAmount.text.toString().toDouble() < 500){
                Toast.makeText(this, resources.getString(R.string.contact_minimum), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            getInstallments()
        }
    }

    private fun getInstallments() {
        contactInstallmentsViewModel.getInstallments(sharedPreferences.nationalId, binding.etAmount.text.toString(), "10")
    }

    private fun observeOnInstallmentsStatus() {
        contactInstallmentsViewModel.installments.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        if (resource.data != null) {
                            val intent = Intent(this@ContactInstallmentsActivity, ContactInvoiceActivity::class.java)
                            intent.putExtra("tenor", resource.data.tenor)
                            intent.putExtra("maxTenor", resource.data.maxTenor)
                            intent.putExtra("instalmentValue", resource.data.instalmentValue)
                            intent.putExtra("amount", binding.etAmount.text.toString())
                            startActivity(intent)
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