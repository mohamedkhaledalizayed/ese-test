package com.neqabty.healthcare.commen.contact_installments.view

import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityContactProvidersBinding
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.searchresult.ItemsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactInstallmentsActivity : BaseActivity<ActivityContactProvidersBinding>() {
    private val mAdapter = ItemsAdapter()
    private val contactInstallmentsViewModel: ContactInstallmentsViewModel by viewModels()

    override fun getViewBinding() = ActivityContactProvidersBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.contact_providers)

        val provider = intent.getParcelableExtra<ProvidersEntity>("provider")
        observeOnInstallmentsStatus()
        getInstallments()

    }

    private fun getInstallments() {
        contactInstallmentsViewModel.getInstallments(sharedPreferences.nationalId, "10", "10")
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