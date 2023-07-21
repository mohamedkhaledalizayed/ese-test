package com.neqabty.healthcare.payment.view.paymentstatus


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.LocaleHelper
import com.neqabty.healthcare.databinding.ActivityPaymentStatusBinding
import com.neqabty.healthcare.invoices.domain.entity.InvoicesEntity
import com.neqabty.healthcare.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.healthcare.payment.view.PaymentViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaymentStatusActivity : BaseActivity<ActivityPaymentStatusBinding>() {

    private lateinit var data: InvoicesEntity
    private val paymentViewModel: PaymentViewModel by viewModels()
    override fun getViewBinding() = ActivityPaymentStatusBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.receipt)


        if (intent.getStringExtra("referenceCode") != null){
            getReceipt()
        }else{
            getInvoice()
        }

        paymentViewModel.paymentStatus.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.healthcare.core.utils.Status.LOADING -> {
                        binding.errorContainer.visibility = View.GONE
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.healthcare.core.utils.Status.SUCCESS -> {
                        binding.layoutContainer.visibility = View.VISIBLE
                        binding.progressCircular.visibility = View.GONE
                        if (!resource.data?.gatewayReferenceId.isNullOrEmpty()){
                            binding.nameValue.text = resource.data?.member_name?: resource.data?.mobile

                            if (resource.data?.itemId != null){
                                binding.membershipNumberValue.text = "${resource.data.itemId}"
                            }else{
                                binding.membershipNumber.visibility = View.GONE
                                binding.membershipNumberValue.visibility = View.GONE
                            }
                            if (!resource.data?.mobile.isNullOrEmpty()){
                                binding.phoneValue.text = "${resource.data?.mobile}"
                            }
                            binding.serviceNameValue.text = "${resource.data?.serviceAction}"
                            binding.receiptNumberValue.text = resource.data?.gatewayReferenceId ?: ""
                            binding.receiptDateValue.text = AppUtils().dateFormat(resource.data!!.createdAt)
                            binding.priceValue.text = "${resource.data.netAmount}  ${getString(R.string.egp)} "
                            binding.feesValue.text = "${resource.data.totalFees}   ${getString(R.string.egp)}"
                            binding.totalValue.text = "${resource.data.totalAmount}   ${getString(R.string.egp)}"

                        }else{
                            binding.receiptLayout.visibility = View.GONE
                            Picasso.get().load(R.drawable.cancel).into(binding.statusImage)
                            binding.statusText.text = resources.getString(R.string.unsuccessfull_payment)
                            binding.statusText.setTextColor(resources.getColor(R.color.cgred))
                            binding.statusDetails.text = resources.getString(R.string.un_paid)
                        }
                    }
                    com.neqabty.healthcare.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.errorContainer.visibility = View.VISIBLE
                        Log.e("test", resource.message.toString())
                        if (resource.message == "404"){
                            Toast.makeText(this, resources.getString(R.string.something_wrong), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

        }

        binding.btnDownload.setOnClickListener {
            var pdfIntent = Intent(this, PdfCreatorScreen::class.java)
            if (intent.getStringExtra("referenceCode") != null){
                pdfIntent.putExtra("data", paymentViewModel.paymentStatus.value?.data)
            }else{
                pdfIntent.putExtra("data",
                    PaymentStatusEntity(
                        entity = data.entity,
                        gatewayReferenceId = data.gatewayReferenceId,
                        itemId = data.membershipId,
                        mobile = data.mobile,
                        netAmount = data.netAmount,
                        serviceAction = data.serviceName,
                        totalAmount = data.totalAmount,
                        totalFees = data.totalFees,
                        member_name = data.fullName,
                        createdAt = data.createdAt
                    )
                )
            }
            startActivity(pdfIntent)
        }

        binding.btnReload.setOnClickListener { getReceipt() }
    }

    private fun getInvoice() {
        data = intent.getParcelableExtra("data")!!
        binding.errorContainer.visibility = View.GONE
        binding.progressCircular.visibility = View.GONE
        binding.layoutContainer.visibility = View.VISIBLE
        binding.nameValue.text = data.fullName

        if (data.membershipId.isNotEmpty()){
            binding.membershipNumberValue.text = data.membershipId
        }else{
            binding.membershipNumber.visibility = View.GONE
            binding.membershipNumberValue.visibility = View.GONE
        }

        binding.phoneValue.text = data.mobile

        binding.serviceNameValue.text = data.serviceName
        binding.receiptNumberValue.text = data.gatewayReferenceId
        binding.receiptDateValue.text = AppUtils().dateFormat(data.createdAt)
        binding.priceValue.text = "${data.netAmount}  ${getString(R.string.egp)} "
        binding.feesValue.text = "${data.totalFees}   ${getString(R.string.egp)}"
        binding.totalValue.text = "${data.totalAmount}   ${getString(R.string.egp)}"
    }

    private fun getReceipt() {
        Handler().postDelayed(Runnable {
            paymentViewModel.getPaymentStatus(intent.getStringExtra("referenceCode")!!)
        }, 9000)
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper.setLocale(this, "ar")
    }

}


