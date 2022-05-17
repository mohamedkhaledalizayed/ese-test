package com.neqabty.superneqabty.paymentdetails.view.paymentdetails

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.core.ui.BaseActivity
import com.neqabty.superneqabty.core.utils.Constants
import com.neqabty.superneqabty.databinding.ActivityPaymentDetailsBinding
import com.neqabty.superneqabty.paymentdetails.data.model.PaymentBody
import com.neqabty.superneqabty.paymentdetails.data.model.PaymentBodyObject
import com.neqabty.superneqabty.paymentdetails.data.model.payment.PaymentResponse
import com.neqabty.superneqabty.paymentdetails.view.PaymentDetailsViewModel
import com.neqabty.superneqabty.paymentdetails.view.paymentstatus.PaymentStatusActivity
import dagger.hilt.android.AndroidEntryPoint
import me.cowpay.PaymentMethodsActivity
import me.cowpay.util.CowpayConstantKeys
import team.opay.business.cashier.sdk.api.*
import team.opay.business.cashier.sdk.pay.PaymentTask


@AndroidEntryPoint
class PaymentDetailsActivity : BaseActivity<ActivityPaymentDetailsBinding>() {

    private val featuresAdapter = FeaturesAdapter()
    private val paymentDetailsViewModel: PaymentDetailsViewModel by viewModels()
    private var paymentMethod = "card"
    private var serviceCode = ""
    private var number = ""
    private var totalAmount = ""
    private var listOfFeatures: ArrayList<Int> = ArrayList()
    override fun getViewBinding() = ActivityPaymentDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.payments)

        binding.rvFeatures.adapter = featuresAdapter
        serviceCode = intent.getStringExtra("code")!!
        number = intent.getStringExtra("number")!!
        paymentDetailsViewModel.getPaymentDetails(serviceCode, number)
        paymentDetailsViewModel.payment.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.superneqabty.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.superneqabty.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.llContent.visibility = View.VISIBLE
                        binding.tvService.text = resource.data?.service?.name
                        binding.tvName.text = "الاسم : ${resource.data?.member?.name}"
                        binding.tvMemberNumber.text = "رقم العضوية : ${intent.getStringExtra("number")!!}"
                        if (resource.data?.receipt == null){
                            binding.cardLayout.visibility = View.GONE
                            binding.tvDetails.visibility = View.GONE
                            binding.tvFeatures.visibility = View.GONE
                            binding.tvPaymentMethod.visibility = View.GONE
                            binding.rgPaymentMechanismType.visibility = View.GONE
                            binding.btnNext.visibility = View.GONE
                            showDialog()
                        }else{
                            val total = paymentDetailsViewModel.payment.value?.data?.receipt?.cardTotalPrice.toString()
                            totalAmount = paymentDetailsViewModel.payment.value?.data?.receipt?.details?.totalPrice.toString()
                            binding.tvAmount.text = "الاجمالى بعد الضريبة : $total"
                            binding.lastFeeYearValue.text = "${resource.data.receipt.details.lastFeeYear} "
                            binding.currentFeeYearValue.text = "${resource.data.receipt.details.currentFeeYear} ج.م "
                            binding.cardPriceValue.text = "${resource.data.receipt.details.cardPrice} ج.م "
                            binding.lateSubscriptionsValue.text = "${resource.data.receipt.details.lateSubscriptions} ج.م "
                            binding.delayFineValue.text = "${resource.data.receipt.details.delayFine} ج.م "
                            binding.totalValue.text = "${resource.data.receipt.details.totalPrice} ج.م "
                            featuresAdapter.submitList(resource.data.service.features)
                        }
                    }
                    com.neqabty.superneqabty.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        paymentDetailsViewModel.getPaymentMethods()
        paymentDetailsViewModel.paymentMethods.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.superneqabty.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.superneqabty.core.utils.Status.SUCCESS -> {
                        if (resource.data?.paymentMethods!!.isNotEmpty()){
                            if(resource.data.paymentMethods.filter { it.name.equals("card") }.get(0).isActive)
                                binding.rbCard.visibility = View.VISIBLE
                            if(resource.data.paymentMethods.filter { it.name.equals("code") }.get(0).isActive)
                                binding.rbChannel.visibility = View.VISIBLE
                            if(resource.data.paymentMethods.filter { it.name.equals("fawry") }.get(0).isActive)
                                binding.rbFawry.visibility = View.VISIBLE
                            if(resource.data.paymentMethods.filter { it.name.equals("wallet") }.get(0).isActive)
                                binding.rbMobileWallet.visibility = View.VISIBLE
                        }
                    }
                    com.neqabty.superneqabty.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        paymentDetailsViewModel.paymentInfo.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.superneqabty.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.superneqabty.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data?.payment?.transaction?.paymentGatewayReferenceId.isNullOrEmpty()){
                            val paymentObject = resource.data as PaymentResponse
                            oPayPayment(paymentObject, true)
                        }else{
                            showAlertDialog( resource.data?.payment?.transaction?.paymentGatewayReferenceId!!)
                        }
                    }
                    com.neqabty.superneqabty.core.utils.Status.ERROR -> {
                        binding.btnNext.isEnabled = true
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }


        featuresAdapter.onItemClickListener = object :
            FeaturesAdapter.OnItemClickListener {
            override fun setOnItemCheckedListener(id: Int) {
                listOfFeatures.add(id)
            }

            override fun setOnItemUnCheckedListener(id: Int) {
                listOfFeatures.remove(id)
            }

        }

        binding.rbCard.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                paymentMethod = "card"
                val total = paymentDetailsViewModel.payment.value?.data?.receipt?.cardTotalPrice.toString()
                binding.ivCard.visibility = View.VISIBLE
                binding.llChannels.visibility = View.GONE
                binding.ivFawry.visibility = View.GONE
                binding.tvAmount.text = "الاجمالى بعد الضريبة : $total"
            }
        }
        binding.rbChannel.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                paymentMethod = "code"
                val total = paymentDetailsViewModel.payment.value?.data?.receipt?.codeTotalPrice.toString()
                binding.llChannels.visibility = View.VISIBLE
                binding.ivCard.visibility = View.GONE
                binding.ivFawry.visibility = View.GONE
                binding.tvAmount.text = "الاجمالى بعد الضريبة : $total"
            }
        }
        binding.rbFawry.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                paymentMethod = "fawry"
                val total = paymentDetailsViewModel.payment.value?.data?.receipt?.codeTotalPrice.toString()
                binding.ivFawry.visibility = View.VISIBLE
                binding.llChannels.visibility = View.GONE
                binding.ivCard.visibility = View.GONE
                binding.tvAmount.text = "الاجمالى بعد الضريبة : $total"
            }
        }

        binding.tvChannels.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://cashier.opaycheckout.com/map")))
        }

        binding.btnNext.setOnClickListener{
            binding.btnNext.isEnabled = false
            paymentDetailsViewModel.getPaymentInfo(PaymentBody(PaymentBodyObject(serviceCode = serviceCode, paymentMethod = paymentMethod, amount = totalAmount, itemId = number.toInt(), service_features = listOfFeatures)))
        }
    }

    private fun showDialog() {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage("لا توجد اي فواتير مستحقه للسداد في الوقت الحالي")
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "موافقة"
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        alertDialog.show()

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
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", paymentGatewayReferenceId)
        clipboard.setPrimaryClip(clip)
    }

    fun cowPayPayment(isCredit: Boolean) {
        var intent = Intent(this, PaymentMethodsActivity::class.java)

        var PaymentMethod = ArrayList<String>()
        if (isCredit)
            PaymentMethod.add(CowpayConstantKeys.CreditCardMethod)
        else
            PaymentMethod.add(CowpayConstantKeys.FawryMethod)
        intent.putExtra(CowpayConstantKeys.PaymentMethod, PaymentMethod)

        intent.putExtra(CowpayConstantKeys.AuthorizationToken, Constants.cowpayAuthToken)

        //set environment production or sandBox
        //CowpayConstantKeys.Production or CowpayConstantKeys.SandBox
        intent.putExtra(CowpayConstantKeys.PaymentEnvironment, Constants.COWPAY_MODE)
        //set locale language
        intent.putExtra(CowpayConstantKeys.Language, CowpayConstantKeys.ENGLISH)
        // use pay with credit card
        intent.putExtra(
            CowpayConstantKeys.CreditCardMethodType,
            CowpayConstantKeys.CreditCardMethodPay
        )

        intent.putExtra(CowpayConstantKeys.MerchantCode, "3GpZbdrsnOrT")
        intent.putExtra(
            CowpayConstantKeys.MerchantHashKey,
            "\$2y\$10$" + "gqYaIfeqefxI162R6NipSucIwvhO9pbksOf0.OP76CVMZEYBPQlha"
        )
        //order id
        intent.putExtra(CowpayConstantKeys.MerchantReferenceId, "anroid_1")
        //order price780
        intent.putExtra(CowpayConstantKeys.Amount, "10")
        //user data
        intent.putExtra(CowpayConstantKeys.Description, "9")
        intent.putExtra(CowpayConstantKeys.CustomerName, sharedPreferences.name)
        intent.putExtra(CowpayConstantKeys.CustomerMobile, sharedPreferences.mobile)
        intent.putExtra(CowpayConstantKeys.CustomerEmail, "customer@customer.com")
        //user id
        intent.putExtra(CowpayConstantKeys.CustomerMerchantProfileId, "anroid_1")


        startActivityForResult(intent, CowpayConstantKeys.PaymentMethodsActivityRequestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PaymentTask.REQUEST_PAYMENT){
            if (resultCode == PaymentTask.RESULT_PAYMENT){
                val response = data?.getSerializableExtra(PaymentTask.RESPONSE_DATA) as WebJsResponse?
                handlePaymentResponse(response)
            }
        }
        if (requestCode == CowpayConstantKeys.PaymentMethodsActivityRequestCode && data != null && resultCode == Activity.RESULT_OK) {
            var responseCode = data.extras!!.getInt(CowpayConstantKeys.ResponseCode, 0)

            if (responseCode == CowpayConstantKeys.ErrorCode) {
                showAlert(getString(R.string.payment_canceled)) {
//                    navController().popBackStack()
//                    navController().navigate(R.id.homeFragment)
                }
            } else if (responseCode == CowpayConstantKeys.SuccessCode) {
                var responseMSG = data.extras!!.getString(CowpayConstantKeys.ResponseMessage)
                var PaymentGatewayReferenceId =
                    data.extras!!.getString(CowpayConstantKeys.PaymentGatewayReferenceId)
                responseMSG?.let {
                    showAlert(if (binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card) getString(R.string.payment_successful) + PaymentGatewayReferenceId else getString(R.string.payment_reference) + PaymentGatewayReferenceId) {
//                        navController().popBackStack()
//                        navController().navigate(R.id.homeFragment)
                    }
                }
            }
        }
    }

    private fun oPayPayment(paymentResponse: PaymentResponse, isCredit: Boolean) {
        referenceCode = paymentResponse.mobilePaymentPayload.reference
        val paymentType = if(isCredit) "BankCard" else "ReferenceCode"
        PaymentTask.sandBox = Constants.OPAY_MODE
        val payInput = PayInput(
            publickey = paymentResponse.mobilePaymentPayload.publickey,
            merchantId = paymentResponse.mobilePaymentPayload.merchantId,
            merchantName = paymentResponse.mobilePaymentPayload.merchantName,
            reference = paymentResponse.mobilePaymentPayload.reference,
            countryCode = paymentResponse.mobilePaymentPayload.countryCode, // uppercase
            currency = paymentResponse.mobilePaymentPayload.currency, // uppercase
            payAmount = (paymentResponse.mobilePaymentPayload.payAmount.toDouble() * 100).toLong(),
            productName = binding.tvService.text.toString(),
            productDescription = binding.tvService.text.toString(),
            callbackUrl = paymentResponse.mobilePaymentPayload.callbackUrl,
            userClientIP = "110.246.160.183",
            expireAt = paymentResponse.mobilePaymentPayload.expireAt,
            paymentType = paymentType // optional
        )

//        llSuperProgressbar.visibility = View.VISIBLE
        PaymentTask(this).createOrder(payInput, callback = { status, response ->
//            llSuperProgressbar.visibility = View.GONE
            when (status) {
                Status.ERROR -> { // error
                    Toast.makeText(this , response.message,
                        Toast.LENGTH_SHORT).show()
                } else -> {
            }
            }
        })
    }

    var referenceCode = ""
    private fun handlePaymentResponse(response: WebJsResponse?) {
        when (response?.orderStatus) {
            PaymentStatus.INITIAL -> {
            }
            PaymentStatus.PENDING -> {
                if (response.eventName.equals("clickResultOKBtn") && binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_channel)
                    showAlert(getString(R.string.payment_reference_without_code)) {
//                        navController().popBackStack()
//                        navController().navigate(R.id.homeFragment)
                    }
            }
            PaymentStatus.SUCCESS -> {

                if (binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card){
                    val intent = Intent(this, PaymentStatusActivity::class.java)
                    intent.putExtra("referenceCode", referenceCode)
                    startActivity(intent)
                    finish()
                }else{
                    showAlert(getString(R.string.payment_reference) + response.referenceCode) {
                        finish()
                    }
                }

                //TODO  Ask Mona About This
//                showAlert(
//                    if (binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card) getString(
//                        R.string.payment_successful
//                    ) + response.orderNo
//                    else getString(R.string.payment_reference) + response.referenceCode
//                ) {
//
//                    finish()
////                    navController().popBackStack()
////                    navController().navigate(R.id.homeFragment)
//                }
            }
            PaymentStatus.FAIL -> {
                showAlert(getString(R.string.payment_canceled)) {
//                    navController().popBackStack()
//                    navController().navigate(R.id.homeFragment)
                }
            }
            PaymentStatus.CLOSE -> {
                showAlert(getString(R.string.payment_canceled)) {
//                    navController().popBackStack()
//                    navController().navigate(R.id.homeFragment)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.btnNext.isEnabled = true
    }
}