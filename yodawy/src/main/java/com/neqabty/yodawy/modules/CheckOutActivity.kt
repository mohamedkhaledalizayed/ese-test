package com.neqabty.yodawy.modules

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.databinding.ActivityCheckOutBinding

class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>() {

    override fun getViewBinding() = ActivityCheckOutBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.place_order)
    }

    fun confirmOrder(view: View) {}
}