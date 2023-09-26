package com.neqabty.healthcare.packages.packageslist.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityPackagesBinding
import com.neqabty.healthcare.packages.packageslist.domain.entity.PackagesEntity
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
        getPackages()
    }

    private fun initializeViews() {
        binding.rvPackages.adapter = packagesAdapter
        packagesAdapter.onItemClickListener = object :
            PackagesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: PackagesEntity) {
                val intent = Intent(this@PackagesActivity, PackageDetailsActivity::class.java)
                intent.putExtra("package", item)
                startActivity(intent)
            }
        }
    }

    private fun getPackages() {
        if (sharedPreferences.code.isEmpty()){
            packagesViewModel.getPackages(Constants.NEQABTY_CODE)
        }else{
            packagesViewModel.getPackages(sharedPreferences.code)
        }
        packagesViewModel.packages.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.rvPackages.visibility = View.VISIBLE
                        packagesAdapter.submitList(resource.data!!.toMutableList())
                        initializeViews()
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        }
    }

}