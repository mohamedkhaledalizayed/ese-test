package com.neqabty.healthcare.modules.offers.presentation.view.offers


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityOffersBinding
import com.neqabty.healthcare.modules.offers.presentation.view.cashback.CashBackActivity
import com.neqabty.healthcare.modules.offers.presentation.view.takeoffer.TakeOfferActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OffersActivity : BaseActivity<ActivityOffersBinding>() {


    private val offersViewModel: OffersViewModel by viewModels()
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


        offersViewModel.getOffers()
        offersViewModel.offers.observe(this){

            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS ->{
                        binding.progressCircular.visibility = View.GONE
                        mAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR ->{
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        }
    }
}