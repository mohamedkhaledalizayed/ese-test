package com.neqabty.healthcare.core.packages


import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPackageRecieptBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PackageReceiptActivity : BaseActivity<ActivityPackageRecieptBinding>() {

    private var mAdapter: FollowersAdapter = FollowersAdapter()
    override fun getViewBinding() = ActivityPackageRecieptBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "معاملة ناجحة")

        binding.followersRecyclerView.adapter = mAdapter

    }
}