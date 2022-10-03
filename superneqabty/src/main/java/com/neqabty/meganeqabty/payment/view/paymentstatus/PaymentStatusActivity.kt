package com.neqabty.meganeqabty.payment.view.paymentstatus


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.core.ui.BaseActivity
import com.neqabty.meganeqabty.R
import com.neqabty.core.utils.AppUtils
import com.neqabty.core.utils.LocaleHelper
import com.neqabty.meganeqabty.databinding.ActivityPaymentStatusBinding
import com.neqabty.meganeqabty.payment.view.PaymentViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class PaymentStatusActivity : BaseActivity<ActivityPaymentStatusBinding>() {

    private val paymentViewModel: PaymentViewModel by viewModels()
    override fun getViewBinding() = ActivityPaymentStatusBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.receipt)

        Handler().postDelayed(Runnable {
            paymentViewModel.getPaymentStatus(intent.getStringExtra("referenceCode")!!)
        }, 2000)

        paymentViewModel.paymentStatus.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.core.utils.Status.SUCCESS -> {
                        binding.layoutContainer.visibility = View.VISIBLE
                        binding.progressCircular.visibility = View.GONE
                        if (!resource.data?.id.isNullOrEmpty()){
                            binding.nameValue.text = resource.data?.member_name?: resource.data?.mobile
                            binding.membershipNumberValue.text = "${resource.data?.itemId}"
                            if (!resource.data?.mobile.isNullOrEmpty()){
                                binding.phoneValue.text = "${resource.data?.mobile}"
                            }
                            binding.syndicateValue.text = "${resource.data?.entity}"
                            binding.serviceNameValue.text = "${resource.data?.serviceAction}"
                            binding.receiptNumberValue.text = resource.data?.gatewayReferenceId ?: ""
                            binding.receiptDateValue.text = AppUtils().dateFormat(resource.data!!.createdAt)
                            binding.priceValue.text = "${resource.data?.netAmount}  ${getString(R.string.egp)} "
                            binding.feesValue.text = "${resource.data?.totalFees}   ${getString(R.string.egp)}"
                            binding.totalValue.text = "${resource.data?.totalAmount}   ${getString(R.string.egp)}"

                        }else{
                            binding.receiptLayout.visibility = View.GONE
                            Picasso.get().load(R.drawable.cancel).into(binding.statusImage)
                            binding.statusText.text = resources.getString(R.string.unsuccessfull_payment)
                            binding.statusText.setTextColor(resources.getColor(R.color.cgred))
                            binding.statusDetails.text = resources.getString(R.string.un_paid)
                        }
                    }
                    com.neqabty.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.message == "404"){
                            Toast.makeText(this, resources.getString(R.string.something_wrong), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

        }

        binding.btnDownload.setOnClickListener {
            var pdfIntent = Intent(this, PdfCreatorScreen::class.java)
            pdfIntent.putExtra("data", paymentViewModel.paymentStatus.value?.data)
            startActivity(pdfIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }

}