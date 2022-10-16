package com.neqabty.healthcare.modules.payment.view


import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.ActivitySehaPaymentBinding
import com.neqabty.meganeqabty.payment.view.paymentstatus.PaymentStatusActivity
import dagger.hilt.android.AndroidEntryPoint
import team.opay.business.cashier.sdk.api.PayInput
import team.opay.business.cashier.sdk.pay.PaymentTask
import team.opay.business.cashier.sdk.api.*
import androidx.activity.viewModels
import com.neqabty.core.ui.BaseActivity
import com.neqabty.healthcare.modules.payment.data.model.Payment
import com.neqabty.healthcare.modules.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.modules.payment.domain.entity.SehaPaymentEntity
import com.neqabty.signup.modules.verifyphonenumber.view.VerifyPhoneActivity

@AndroidEntryPoint
class SehaPaymentActivity : BaseActivity<ActivitySehaPaymentBinding>() {
    private var paymentMethod = "card"
    private var totalAmount = 0
    private var paymentFees = 10
    private var serviceCode = ""
    private var serviceActionCode = ""
    private val paymentViewModel: SehaPaymentViewModel by viewModels()
    override fun getViewBinding() = ActivitySehaPaymentBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "المدفوعات")

        totalAmount = intent.getDoubleExtra("price", 0.0).toInt()
        serviceCode = intent.getStringExtra("serviceCode")!!
        serviceActionCode = intent.getStringExtra("serviceActionCode")!!
        binding.tvPackageName.text = "اسم الباقة : ${intent.getStringExtra("name")}"
        binding.tvPackagePrice.text = "سعر الباقة : $totalAmount"
        updateTotal()
        paymentViewModel.getPaymentMethods()
        paymentViewModel.paymentMethods.observe(this) { it ->

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.llContent.visibility = View.VISIBLE
                        if (resource.data!!.isNotEmpty()) {
                            if (resource.data!!.filter { it.name == "card" }[0].isActive)
                                binding.rbCard.visibility = View.VISIBLE
                            if (resource.data!!.filter { it.name == "code" }[0].isActive)
                                binding.rbChannel.visibility = View.VISIBLE
                        }
                    }
                    com.neqabty.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        paymentViewModel.paymentInfo.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data?.payment?.transaction?.paymentGatewayReferenceId.isNullOrEmpty()) {
                            val paymentObject = resource.data as SehaPaymentEntity
                            oPayPayment(paymentObject, true)
                        } else {
                            showAlertDialog(resource.data?.payment?.transaction?.paymentGatewayReferenceId!!)
                        }
                    }
                    com.neqabty.core.utils.Status.ERROR -> {
                        binding.btnNext.isEnabled = true
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }


        binding.rbCard.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                paymentMethod = "card"
                binding.ivCard.visibility = View.VISIBLE
                binding.llChannels.visibility = View.GONE
                binding.ivFawry.visibility = View.GONE
            }
        }
        binding.rbChannel.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                paymentMethod = "code"
                binding.llChannels.visibility = View.VISIBLE
                binding.ivCard.visibility = View.GONE
                binding.ivFawry.visibility = View.GONE
            }
        }
        binding.rbFawry.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                paymentMethod = "fawry"
                binding.ivFawry.visibility = View.VISIBLE
                binding.llChannels.visibility = View.GONE
                binding.ivCard.visibility = View.GONE
            }
        }

        binding.tvChannels.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://cashier.opaycheckout.com/map")
                )
            )
        }

        binding.btnNext.setOnClickListener {

            if (sharedPreferences.isPhoneVerified) {

                binding.btnNext.isEnabled = false
                paymentViewModel.getPaymentInfo(
                    SehaPaymentBody(
                        Payment(
                            serviceCode = serviceCode,
                            serviceActionCode = serviceActionCode,
                            mobile = sharedPreferences.mobile,
                            paymentMethod = paymentMethod,
                            paymentSource = "android",
                            transactionType = "payment"
                        )
                    )
                )
            }else{
                verifyPhone()
            }

        }
    }

    private fun verifyPhone() {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(com.neqabty.meganeqabty.R.string.alert))
        alertDialog.setMessage(resources.getString(com.neqabty.meganeqabty.R.string.confirm_phone))
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, resources.getString(com.neqabty.meganeqabty.R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            val intent = Intent(this, VerifyPhoneActivity::class.java)
            startActivity(intent)
        }
        alertDialog.show()

    }

    private fun updateTotal(){
        binding.tvAmount.text = "الاجمالى بعد الرسوم البنكية والاداريه : ${totalAmount + paymentFees}"
    }

    var referenceCode = ""
    private fun oPayPayment(paymentEntity: SehaPaymentEntity, isCredit: Boolean) {
        referenceCode = paymentEntity.mobilePaymentPayload!!.reference
        val paymentType = if (isCredit) "BankCard" else "ReferenceCode"
        PaymentTask.sandBox = false
        val payInput = PayInput(
            publickey = paymentEntity.mobilePaymentPayload.publickey,
            merchantId = paymentEntity.mobilePaymentPayload.merchantId,
            merchantName = paymentEntity.mobilePaymentPayload.merchantName,
            reference = paymentEntity.mobilePaymentPayload.reference,
            countryCode = paymentEntity.mobilePaymentPayload.countryCode, // uppercase
            currency = paymentEntity.mobilePaymentPayload.currency, // uppercase
            payAmount = (paymentEntity.mobilePaymentPayload.payAmount.toDouble() * 100).toLong(),
            productName = binding.tvPackageName.text.toString(),
            productDescription = binding.tvPackageName.text.toString(),
            callbackUrl = paymentEntity.mobilePaymentPayload.callbackUrl,
            userClientIP = "110.246.160.183",
            expireAt = paymentEntity.mobilePaymentPayload.expireAt,
            paymentType = paymentType // optional
        )

        PaymentTask(this).createOrder(payInput, callback = { status, response ->
            when (status) {
                Status.ERROR -> {
                    Toast.makeText(
                        this, response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PaymentTask.REQUEST_PAYMENT) {
            if (resultCode == PaymentTask.RESULT_PAYMENT) {
                val response =
                    data?.getSerializableExtra(PaymentTask.RESPONSE_DATA) as WebJsResponse?
                handlePaymentResponse(response)
            }
        }
    }

    private fun showAlert(
        message: String,
        title: String = getString(R.string.alert_title)) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
                dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun handlePaymentResponse(response: WebJsResponse?) {
        binding.btnNext.isEnabled = true
        when (response?.orderStatus) {
            PaymentStatus.INITIAL -> {
            }
            PaymentStatus.PENDING -> {
                if (response.eventName.equals("clickResultOKBtn") && binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_channel)
                    showAlert(getString(R.string.payment_reference_without_code))
            }
            PaymentStatus.SUCCESS -> {

                if (binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card) {
                    val intent = Intent(this, PaymentStatusActivity::class.java)
                    intent.putExtra("referenceCode", referenceCode)
                    startActivity(intent)
                    finish()
                } else {
                    showAlert(getString(R.string.payment_reference) + response.referenceCode)
                }

            }
            PaymentStatus.FAIL -> {
                showAlert(getString(R.string.payment_canceled))
            }
            PaymentStatus.CLOSE -> {
                showAlert(getString(R.string.payment_canceled))
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