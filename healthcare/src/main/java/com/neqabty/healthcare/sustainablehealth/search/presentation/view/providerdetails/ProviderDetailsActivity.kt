package com.neqabty.healthcare.sustainablehealth.search.presentation.view.providerdetails

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.neqabty.chefaa.modules.orders.domain.entities.OrderEntity
import com.neqabty.healthcare.databinding.ActivityProviderDetailsBinding
import com.neqabty.healthcare.core.ui.BaseActivity
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

        binding.itemName.text =  "${provider?.name}"
        binding.addressValue.text =  "${provider?.address}"
        binding.phoneValue.text =  "${provider?.phone}"
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
}