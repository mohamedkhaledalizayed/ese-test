package com.neqabty.meganeqabty.payment.view.paymentstatus


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.core.ui.BaseActivity
import com.neqabty.meganeqabty.core.utils.AppUtils
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

        setupToolbar(title = "الفاتورة")

        paymentViewModel.getPaymentStatus(intent.getStringExtra("referenceCode")!!)
        paymentViewModel.paymentStatus.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.meganeqabty.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.meganeqabty.core.utils.Status.SUCCESS -> {
                        binding.layoutContainer.visibility = View.VISIBLE
                        binding.progressCircular.visibility = View.GONE
                        if (!resource.data?.id.isNullOrEmpty()){
                            binding.nameValue.text = "${resource.data?.member_name}"
                            binding.membershipNumberValue.text = "${resource.data?.itemId}"
                            if (!resource.data?.mobile.isNullOrEmpty()){
                                binding.phoneValue.text = "${resource.data?.mobile}"
                            }
                            binding.syndicateValue.text = "${resource.data?.entity}"
                            binding.serviceNameValue.text = "${resource.data?.serviceAction}"
                            binding.receiptNumberValue.text = "${resource.data?.gatewayReferenceId}"
                            binding.receiptDateValue.text = AppUtils().dateFormat(resource.data!!.createdAt)
                            binding.priceValue.text = "${resource.data?.netAmount} جنيه "
                            binding.feesValue.text = "${resource.data?.totalFees}  جنيه"
                            binding.totalValue.text = "${resource.data?.totalAmount}  جنيه"

                        }else{
                            binding.receiptLayout.visibility = View.GONE
                            Picasso.get().load(R.drawable.cancel).into(binding.statusImage)
                            binding.statusText.text = "عملية فاشلة."
                            binding.statusText.setTextColor(resources.getColor(R.color.cgred))
                            binding.statusDetails.text = "لم يتم دفع الفاتورة."
                        }
                    }
                    com.neqabty.meganeqabty.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Log.e("test", resource.message.toString())
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

}