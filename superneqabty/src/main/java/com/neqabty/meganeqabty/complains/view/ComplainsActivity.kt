package com.neqabty.meganeqabty.complains.view


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.databinding.ActivityComplainsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComplainsActivity : BaseActivity<ActivityComplainsBinding>() {

    private val mAdapter = ComplainsAdapter()
    override fun getViewBinding() = ActivityComplainsBinding.inflate(layoutInflater)
    private val complainsViewModel: ComplainsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.complains)
        binding.complainsRecycler.adapter = mAdapter
        complainsViewModel.getAllComplains()
        complainsViewModel.complainStatus.observe(this){
            it?.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        mAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }

        }

    }
}