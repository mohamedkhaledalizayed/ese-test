package com.neqabty.healthcare.core.syndicates

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.home_syndicates.view.SyndicatesHomeActivity
import com.neqabty.healthcare.core.more.view.MoreActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySyndicatesBinding
import com.neqabty.healthcare.invoices.view.InvoicesActivity
import com.neqabty.healthcare.onboarding.signup.data.SignupData
import com.neqabty.healthcare.onboarding.signup.view.SignupActivity
import com.neqabty.healthcare.onboarding.signup.view.SyndicatesAdapter
import com.neqabty.healthcare.pharmacy.PharmacyTermsBottomSheet
import com.neqabty.healthcare.profile.view.profile.ProfileActivity
import com.neqabty.healthcare.splash.view.SplashActivity
import com.neqabty.healthcare.syndicates.domain.entity.SyndicateEntity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SyndicatesActivity : BaseActivity<ActivitySyndicatesBinding>(), ISignUp {
    private val syndicatesViewModel: SyndicatesViewModel by viewModels()
    private val syndicatesAdapter = SyndicatesAdapter(hasCustomStyle = true)
    private lateinit var  bottomSheetFragment: SyndicateServiceBottomSheet
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
                SignupData.syndicateID = item.code
                SignupData.syndicateName = item.name
                bottomSheetFragment = SyndicateServiceBottomSheet.newInstance(item)
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }
        }

        binding.ivProfileNav.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
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
                    val intent = Intent(this, InvoicesActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.navigation_more -> {
                    val intent = Intent(this, MoreActivity::class.java)
                    startActivity(intent)
                    finish()
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
                        SignupData.syndicatesList = resource.data!!.toMutableList()
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

    override fun onSignUpClicked() {
        if (sharedPreferences.isSyndicateMember){
            sharedPreferences.clearAll()
            startActivity(Intent(this, SplashActivity::class.java))
            finishAffinity()
        }else{
            sharedPreferences.isSkippedToHome = false
            val intent = Intent(this, SignupActivity::class.java)
            if(!sharedPreferences.mobile.isNullOrBlank())
                intent.putExtra("isSyndicate", true)
            startActivity(intent)
            finishAffinity()
        }
    }
    //endregion
}