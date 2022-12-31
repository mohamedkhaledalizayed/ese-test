package com.neqabty.healthcare.sustainablehealth.search.presentation.view.providerdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityProviderDetailsBinding
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.search.ProvidersEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProviderDetailsActivity : BaseActivity<ActivityProviderDetailsBinding>() {

    private val mAdapter = ReviewsAdapter()
    override fun getViewBinding() = ActivityProviderDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val provider = intent.getParcelableExtra<ProvidersEntity>("provider")
        setupToolbar(title = "${provider?.name}")

        if (provider?.serviceProviderType?.id == 2){
            binding.government.text = "سعر الكشف ${provider.price}"
        }else{
            binding.government.text = "نسبة الخصم ${provider?.price}"
        }

        binding.itemName.text = "${provider?.name}"
        binding.addressValue.text = "${provider?.address}"
        binding.phoneValue.text = "${provider?.phone}"
        binding.mobileValue.text = "${provider?.mobile}"
        binding.phoneValue.setOnClickListener { provider?.phone?.let { it -> openPhonesFragment(it) } }
        if(!provider?.notes.isNullOrBlank()) {
            binding.notes.visibility = View.VISIBLE
            binding.notesValue.text = provider?.notes
        }
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
    }

    private fun openPhonesFragment(phones: String) {
        val fm: FragmentManager = supportFragmentManager
        val dialog = PhonesFragment.newInstance(phones)
        dialog.show(fm, "")
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent)
    }
}