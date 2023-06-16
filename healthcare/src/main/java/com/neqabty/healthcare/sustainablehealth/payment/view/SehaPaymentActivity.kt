package com.neqabty.healthcare.sustainablehealth.payment.view


import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.otp.view.VerifyPhoneActivity
import com.neqabty.healthcare.core.data.Constants.SANDBOX
import com.neqabty.healthcare.core.packages.PaymentMethodsAdapter
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivitySehaPaymentBinding
import com.neqabty.healthcare.mega.payment.data.model.inquiryresponse.GatewaysData
import com.neqabty.healthcare.mega.payment.view.paymentstatus.PaymentStatusActivity
import com.neqabty.healthcare.sustainablehealth.payment.data.model.Payment
import com.neqabty.healthcare.sustainablehealth.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.sustainablehealth.payment.data.model.sehapayment.SehaPaymentResponse
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.SehaPaymentEntity
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.paymentmethods.PaymentMethodEntity
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import dagger.hilt.android.AndroidEntryPoint
import team.opay.business.cashier.sdk.api.*
import team.opay.business.cashier.sdk.pay.PaymentTask


@AndroidEntryPoint
class SehaPaymentActivity : BaseActivity<ActivitySehaPaymentBinding>(), CallbackPaymentInterface {
    private var paymentMethod = ""
    private var deliveryMethod = 0
    private var deliveryFees = 0.0
    private var totalAmount = 0
    private var vat = 0
    private var total = 0
    private var paymentFees = 10
    private var serviceCode = ""
    private var serviceActionCode = ""
    private var mAdapter = PaymentMethodsAdapter()
    private val paymentViewModel: SehaPaymentViewModel by viewModels()
    override fun getViewBinding() = ActivitySehaPaymentBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "دفع الاشتراك")

        totalAmount = intent.getDoubleExtra("price", 0.0).toInt()
        vat = intent.getDoubleExtra("vat", 0.0).toInt()
        total = intent.getDoubleExtra("total", 0.0).toInt()
        serviceCode = intent.getStringExtra("serviceCode")!!
        serviceActionCode = intent.getStringExtra("serviceActionCode")!!
        binding.tvPackagePriceValue.text = "$totalAmount جنيه"
        binding.tvVatValue.text = "$vat جنيه"
        binding.tvTotal.text = "$total جنيه"

        binding.paymentMethods.adapter = mAdapter
        mAdapter.onItemClickListener = object : PaymentMethodsAdapter.OnItemClickListener{
            override fun setOnItemClickListener(item: PaymentMethodEntity) {
                paymentMethod = item.name
                mAdapter.notifyDataSetChanged()
            }

        }
        paymentViewModel.getPaymentMethods(serviceCode)
        paymentViewModel.paymentMethods.observe(this) { it ->

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.healthcare.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.healthcare.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.llContent.visibility = View.VISIBLE
//                        createRadioButton(resource.data!!.paymentMethods)
                        deliveryMethod = resource.data!!.deliveryMethods.id
                        deliveryFees = resource.data.deliveryMethods.price
                        mAdapter.submitList(resource.data.paymentMethods)
                        updateTotal()
                    }
                    com.neqabty.healthcare.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }

        }

        paymentViewModel.paymentInfo.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.healthcare.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.healthcare.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        when (resource.data?.payment_method) {
                            "Opay Card" -> {
                                oPayPayment(resource.data)
                            }
                            "Opay Code" -> {
                                showAlertDialog(resource.data.payment_gateway_transaction_num ?: "")
                            }
                            "Fawry Code" -> {
                                showAlertDialog(resource.data.payment_gateway_transaction_num ?: "")
                            }
                            else -> {

                            }
                        }
                    }
                    com.neqabty.healthcare.core.utils.Status.ERROR -> {
                        binding.btnNext.isEnabled = true
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }

        }


//        binding.rbCard.setOnCheckedChangeListener { compoundButton, b ->
//            if (b) {
//                paymentMethod = "card"
//                binding.ivCard.visibility = View.VISIBLE
//                binding.llChannels.visibility = View.GONE
//                binding.ivFawry.visibility = View.GONE
//            }
//        }
//        binding.rbChannel.setOnCheckedChangeListener { compoundButton, b ->
//            if (b) {
//                paymentMethod = "code"
//                binding.llChannels.visibility = View.VISIBLE
//                binding.ivCard.visibility = View.GONE
//                binding.ivFawry.visibility = View.GONE
//            }
//        }
//        binding.rbFawry.setOnCheckedChangeListener { compoundButton, b ->
//            if (b) {
//                paymentMethod = "fawry"
//                binding.ivFawry.visibility = View.VISIBLE
//                binding.llChannels.visibility = View.GONE
//                binding.ivCard.visibility = View.GONE
//            }
//        }
//
//        binding.tvChannels.setOnClickListener {
//            startActivity(
//                Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("https://cashier.opaycheckout.com/map")
//                )
//            )
//        }

        binding.btnNext.setOnClickListener {

            if (paymentMethod.isEmpty()){
                Toast.makeText(this, "من فضلك اختر طريقة الدفع.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (binding.address.text.toString().isEmpty()) {
                Toast.makeText(this, resources.getString(R.string.enter_add), Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (sharedPreferences.isPhoneVerified) {

                binding.btnNext.isEnabled = false
                paymentViewModel.getPaymentInfo(
                    SehaPaymentBody(
                        serviceCode = serviceCode,
                        serviceActionCode = serviceActionCode,
                        paymentMethod = paymentMethod,
                        mobile = sharedPreferences.mobile,
                        deliveryMethod = deliveryMethod,
                        address = binding.address.text.toString()
                    )
                )
            }else{
                verifyPhone()
            }

        }
    }

    private fun verifyPhone() {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(resources.getString(R.string.confirm_phone))
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            val intent = Intent(this, VerifyPhoneActivity::class.java)
            startActivity(intent)
        }
        alertDialog.show()

    }

    private fun updateTotal(){
        binding.tvAmount.text = "الاجمالى بعد الرسوم البنكية والاداريه : ${total + paymentFees + deliveryFees} جنيه"
    }

    var referenceCode = ""
    private fun oPayPayment(paymentEntity: SehaPaymentResponse) {
        referenceCode = paymentEntity.payment_gateway_transaction_num ?: ""
        PaymentTask.sandBox = SANDBOX
        val payInput = PayInput(
            publickey = paymentEntity.public_key ?: "",
            merchantId = paymentEntity.merchant_id ?: "",
            merchantName = "Neqabty",
            reference = paymentEntity.payment_gateway_transaction_num ?: "",
            countryCode = "EG", // uppercase
            currency = "EGP", // uppercase
            payAmount = (paymentEntity.total_amount!!.toDouble() * 100).toLong(),
            productName = "${intent.getStringExtra("name")}",
            productDescription = "${intent.getStringExtra("name")}",
            callbackUrl = paymentEntity.callBackURL ?: "",
            userClientIP = "110.246.160.183",
            expireAt = (paymentEntity.expireAt ?: "30").toInt(),
            paymentType = "BankCard" // optional
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

            }
            PaymentStatus.SUCCESS -> {
                val intent = Intent(this, PaymentStatusActivity::class.java)
                intent.putExtra("referenceCode", referenceCode)
                startActivity(intent)
                finish()
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

    override fun onError(error: PaymentSdkError) {
        showAlert(getString(R.string.payment_canceled)) {
            finish()
        }
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {
            val intent = Intent(this, PaymentStatusActivity::class.java)
            intent.putExtra("referenceCode", referenceCode)
            startActivity(intent)
            finish()
    }

    override fun onPaymentCancel() {
        showAlert(getString(R.string.payment_canceled)) {
            finish()
        }
    }
    //endregion
}