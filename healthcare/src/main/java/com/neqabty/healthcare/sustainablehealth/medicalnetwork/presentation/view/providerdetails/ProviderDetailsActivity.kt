package com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.providerdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.neqabty.healthcare.commen.contact_installments.view.ContactInstallmentsActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityProviderDetailsBinding
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.ProvidersEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProviderDetailsActivity : BaseActivity<ActivityProviderDetailsBinding>() {

    private val mAdapter = ReviewsAdapter()
    override fun getViewBinding() = ActivityProviderDetailsBinding.inflate(layoutInflater)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val provider = intent.getParcelableExtra<ProvidersEntity>("provider")
        setupToolbar(title = "${provider?.name}")

        if (provider?.serviceProviderType?.providerTypeEn == "Doctors"){
            binding.government.text = "سعر الكشف : ${provider.price} جنيه"
        }else{
            binding.government.text = "نسبة الخصم : ${provider?.price}"
        }

        binding.itemName.text = "${provider?.name}"
        binding.addressValue.text = "${provider?.governorate?.governorateAr}, ${provider?.area?.areaName}, ${provider?.address}"
        binding.phoneValue.text = "${provider?.phone}"
        binding.mobileValue.text = "${provider?.mobile}"

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

        binding.addReviewBtn.setOnClickListener {
            val fm: FragmentManager = supportFragmentManager
            val dialog = AddReviewDailog()
            dialog.show(fm, "")
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
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
}