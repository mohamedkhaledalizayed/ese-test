package com.neqabty.healthcare.modules.wallet.presentation



import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityWalletBinding

class WalletActivity : BaseActivity<ActivityWalletBinding>() {

    override fun getViewBinding() = ActivityWalletBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "المحفظة")
    }
}