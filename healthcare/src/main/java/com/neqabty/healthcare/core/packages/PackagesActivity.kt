package com.neqabty.healthcare.core.packages

import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityPackagesBinding
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.packages.PackagesEntity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PackagesActivity : BaseActivity<ActivityPackagesBinding>() {
    private val packagesViewModel: PackagesViewModel by viewModels()
    private val packagesAdapter = PackagesAdapter()
    override fun getViewBinding() = ActivityPackagesBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupToolbar(titleResId = R.string.packages)
        showProgressDialog()
        getPackages()
    }

    private fun initializeViews() {
        binding.rvPackages.adapter = packagesAdapter
        packagesAdapter.onItemClickListener = object :
            PackagesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: PackagesEntity) {
            }
        }
    }

    private fun getPackages() {
        packagesViewModel.getPackages(Constants.NEQABTY_CODE)
        packagesViewModel.packages.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        packagesAdapter.submitList(resource.data!!.toMutableList())
                        initializeViews()
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        getPackages()
                    }
                }
            }
        }
    }

    //region
    //endregion
}