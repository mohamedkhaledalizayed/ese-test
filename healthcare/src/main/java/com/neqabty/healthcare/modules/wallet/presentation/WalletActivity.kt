package com.neqabty.healthcare.modules.wallet.presentation



import android.os.Bundle
import com.neqabty.core.ui.BaseActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.ActivityWalletBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletActivity : BaseActivity<ActivityWalletBinding>() {

    override fun getViewBinding() = ActivityWalletBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.wallet)
    }
}