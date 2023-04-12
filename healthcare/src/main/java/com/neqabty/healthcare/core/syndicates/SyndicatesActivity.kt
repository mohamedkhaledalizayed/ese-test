package com.neqabty.healthcare.core.syndicates

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.onboarding.signup.data.SignupData
import com.neqabty.healthcare.commen.onboarding.signup.view.SyndicatesAdapter
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.core.more.view.MoreActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySyndicatesBinding
import com.neqabty.healthcare.mega.payment.view.selectservice.PaymentsActivity
import com.neqabty.healthcare.sustainablehealth.mypackages.presentation.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SyndicatesActivity : BaseActivity<ActivitySyndicatesBinding>() {
    private val syndicatesViewModel: SyndicatesViewModel by viewModels()
    private val syndicatesAdapter = SyndicatesAdapter()
    override fun getViewBinding() = ActivitySyndicatesBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupToolbar(titleResId = R.string.syndicates)
        showProgressDialog()
        getSyndicates()
    }

    private fun initializeViews() {
        binding.bnvSyndicatesHome.selectedItemId = R.id.navigation_syndicates

        binding.rvSyndicates.adapter = syndicatesAdapter
        syndicatesAdapter.onItemClickListener = object :
            SyndicatesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: SyndicateEntity) {
            }
        }

        binding.ivProfileNav.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.bnvSyndicatesHome.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    finish()
                    true
                }
                R.id.navigation_syndicates -> {
                    true
                }
                R.id.navigation_payments -> {
                    val intent = Intent(this, PaymentsActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.navigation_more -> {
                    val intent = Intent(this, MoreActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun getSyndicates() {
        syndicatesViewModel.getSyndicates()
        syndicatesViewModel.syndicates.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        syndicatesAdapter.submitList(resource.data!!.toMutableList())
                        initializeViews()
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        getSyndicates()
                    }
                }
            }
        }
    }

    //region
    override fun onResume() {
        super.onResume()

        binding.bnvSyndicatesHome.selectedItemId = R.id.navigation_syndicates
    }
    //endregion
}