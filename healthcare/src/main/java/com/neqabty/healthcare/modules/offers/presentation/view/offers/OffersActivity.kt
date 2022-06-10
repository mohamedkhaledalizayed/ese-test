package com.neqabty.healthcare.modules.offers.presentation.view.offers


import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityOffersBinding
import com.neqabty.healthcare.modules.offers.presentation.view.cashback.CashBackActivity
import com.neqabty.healthcare.modules.offers.presentation.view.takeoffer.TakeOfferActivity


class OffersActivity : BaseActivity<ActivityOffersBinding>() {


    private val mAdapter = OffersAdapter()
    override fun getViewBinding() = ActivityOffersBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "الخصومات والعروض")


        binding.offersRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            OffersAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {

            }

            override fun setOnTakeClickListener(item: String) {
                startActivity(Intent(this@OffersActivity, TakeOfferActivity::class.java))
            }

            override fun setOnCashBackClickListener(item: String) {
                startActivity(Intent(this@OffersActivity, CashBackActivity::class.java))
            }
        }
    }
}