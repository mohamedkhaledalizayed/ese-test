package com.neqabty.shealth.sustainablehealth.wallet.presentation



import android.os.Bundle
import com.neqabty.shealth.core.ui.BaseActivity
import com.neqabty.shealth.R
import com.neqabty.shealth.databinding.ActivityWalletBinding
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