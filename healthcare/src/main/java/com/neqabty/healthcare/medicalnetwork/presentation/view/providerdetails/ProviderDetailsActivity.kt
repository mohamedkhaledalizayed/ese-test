package com.neqabty.healthcare.medicalnetwork.presentation.view.providerdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.neqabty.healthcare.contact.contact_installments.view.ContactInstallmentsActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityProviderDetailsBinding
import com.neqabty.healthcare.medicalnetwork.domain.entity.search.ProvidersEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProviderDetailsActivity : BaseActivity<ActivityProviderDetailsBinding>() {
    private val providerDetailsViewModel: ProviderDetailsViewModel by viewModels()

    private val mAdapter = ReviewsAdapter()
    override fun getViewBinding() = ActivityProviderDetailsBinding.inflate(layoutInflater)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val provider = intent.getParcelableExtra<ProvidersEntity>("provider")
        setupToolbar(title = "${provider?.name}")
        observeMedicalProviderDetails()
        providerDetailsViewModel.getProviderDetails(provider?.id.toString())
    }

    private fun initializeViews(provider: ProvidersEntity){
        if (provider?.serviceProviderType?.providerTypeEn == "Doctors"){
            binding.government.text = "سعر الكشف : ${provider.price} جنيه"
        }else{
            binding.government.text = "نسبة الخصم : ${provider?.price}"
        }

        binding.itemName.text = "${provider?.name}"
        binding.addressValue.text = "${provider?.governorate?.governorateAr}, ${provider?.area?.areaName}, ${provider?.address}"
        binding.phoneValue.text = "${provider?.phone}"
        binding.phoneValue.text = "${provider?.phone}"
        binding.phoneValue.visibility = if(provider?.phone.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.phone.visibility = if(provider?.phone.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.mobileValue.text = "${provider?.mobile}"
        binding.mobileValue.visibility = if(provider?.mobile.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.mobile.visibility = if(provider?.mobile.isNullOrEmpty()) View.GONE else View.VISIBLE

        if (provider?.profession?.professionName.isNullOrEmpty()){
            binding.profession.visibility = View.GONE
            binding.professionValue.visibility = View.GONE
        }else{
            binding.professionValue.text = provider?.profession?.professionName
        }

        if (provider?.degree?.degreeName.isNullOrEmpty()){
            binding.degree.visibility = View.GONE
            binding.degreeValue.visibility = View.GONE
        }else{
            binding.degreeValue.text = provider?.degree?.degreeName
        }
        binding.phoneValue.setOnClickListener {
            if (provider?.phone != "لا يوجد"){
                openPhonesFragment(provider?.phone!!)
            }
        }
        binding.mobileValue.setOnClickListener {
            if (provider?.mobile != "لا يوجد"){
                openPhonesFragment(provider?.mobile!!)
            }
        }
        binding.notesValue.text = provider?.notes
        binding.providerSp.text =  "${provider?.serviceProviderType?.providerTypeAr}"
        binding.reviewRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            ReviewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {
            }

            override fun setOnRegisterClickListener(item: String) {
            }
        }

        binding.bContact.visibility = if(provider?.hasQR == true) View.VISIBLE else View.GONE
        binding.bContact.setOnClickListener {
            val intent = Intent(this@ProviderDetailsActivity, ContactInstallmentsActivity::class.java)
            intent.putExtra("provider", intent.getParcelableExtra<ProvidersEntity>("provider"))
            startActivity(intent)
        }
    }

    private fun openPhonesFragment(phones: String) {
        val fm: FragmentManager = supportFragmentManager
        val dialog = PhonesFragment.newInstance(phones)
        dialog.show(fm, "")
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent)
    }

    private fun observeMedicalProviderDetails() {
        providerDetailsViewModel.providerDetails.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        binding.providerDetails.visibility = View.VISIBLE
                        initializeViews(resource.data!!)
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                    }
                }
            }
        }
    }
}