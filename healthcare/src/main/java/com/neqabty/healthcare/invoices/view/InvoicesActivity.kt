package com.neqabty.healthcare.invoices.view

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityInvoicesBinding
import com.neqabty.healthcare.invoices.domain.entity.InvoicesEntity
import com.neqabty.healthcare.payment.view.paymentstatus.PaymentStatusActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvoicesActivity : BaseActivity<ActivityInvoicesBinding>() {

    private val invoicesAdapter: InvoicesAdapter = InvoicesAdapter()
    private val viewModel: InvoicesViewModel by viewModels()
    override fun getViewBinding() = ActivityInvoicesBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.payments_title)
        viewModel.getAllInvoices()

        binding.headerId.setOnClickListener { finish() }
        invoicesAdapter.onItemClickListener = object : InvoicesAdapter.OnItemClickListener{
            override fun setOnItemClickListener(item: InvoicesEntity) {
                if (item.status == "PENDING"){
                    showAlertDialog(item.gatewayReferenceId)
                }else{
                    val intent = Intent(this@InvoicesActivity, PaymentStatusActivity::class.java)
                    intent.putExtra("data", item)
                    startActivity(intent)
                }
            }

        }
        binding.invoicesRecyclerView.adapter = invoicesAdapter
        viewModel.invoices.observe(this){
            when(it.status){
                Status.LOADING ->{
                    binding.progressCircular.visibility = View.VISIBLE
                }
                Status.SUCCESS-> {
                    binding.progressCircular.visibility = View.GONE
                    invoicesAdapter.submitList(it.data?.toMutableList())
                }
                Status.ERROR ->{
                    binding.progressCircular.visibility = View.GONE
                }
            }
        }

    }

    private fun showAlertDialog(paymentGatewayReferenceId: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(getString(R.string.payment_reference) + " $paymentGatewayReferenceId")
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.copy_text)
        ) { dialog, _ ->
            dialog.dismiss()
            copyText(paymentGatewayReferenceId)
        }
        alertDialog.show()

    }

    private fun copyText(paymentGatewayReferenceId: String) {
        val clipboard: ClipboardManager =
            getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", paymentGatewayReferenceId)
        clipboard.setPrimaryClip(clip)
    }

}




