package com.neqabty.healthcare.commen.invoices.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityInvoicesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvoicesActivity : BaseActivity<ActivityInvoicesBinding>() {

    private val viewModel: InvoicesViewModel by viewModels()
    override fun getViewBinding() = ActivityInvoicesBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.payments_title)
        viewModel.getAllInvoices()

        viewModel.invoices.observe(this){
            when(it.status){
                Status.LOADING ->{

                }
                Status.SUCCESS-> {
                    Log.e("test", it.data.toString())
                }
                Status.ERROR ->{

                }
            }
        }

    }
}