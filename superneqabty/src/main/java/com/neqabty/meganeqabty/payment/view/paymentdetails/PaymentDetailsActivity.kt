package com.neqabty.meganeqabty.payment.view.paymentdetails

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.core.ui.BaseActivity
import com.neqabty.meganeqabty.core.utils.Constants
import com.neqabty.meganeqabty.databinding.ActivityPaymentDetailsBinding
import com.neqabty.meganeqabty.payment.data.model.PaymentBody
import com.neqabty.meganeqabty.payment.data.model.PaymentBodyObject
import com.neqabty.meganeqabty.payment.domain.entity.branches.BranchesEntity
import com.neqabty.meganeqabty.payment.domain.entity.payment.PaymentEntity
import com.neqabty.meganeqabty.payment.view.PaymentViewModel
import com.neqabty.meganeqabty.payment.view.paymentstatus.PaymentStatusActivity
import com.neqabty.meganeqabty.profile.view.update.UpdateInfoActivity
import dagger.hilt.android.AndroidEntryPoint
import me.cowpay.PaymentMethodsActivity
import me.cowpay.util.CowpayConstantKeys
import team.opay.business.cashier.sdk.api.*
import team.opay.business.cashier.sdk.pay.PaymentTask


@AndroidEntryPoint
class PaymentDetailsActivity : BaseActivity<ActivityPaymentDetailsBinding>() {

    private val paymentViewModel: PaymentViewModel by viewModels()
    private var paymentMethod = "card"
    private var serviceCode = ""
    private var serviceActionCode = ""
    private var number = ""
    private var totalAmount = 0
    private var deliveryFees = 0
    private var paymentFees = 0
    private val branchesAdapter = BranchesAdapter()
    private var deliveryMethod = 1
    private var address = ""
    private var entityBranch = 1
    private var branchesList: List<BranchesEntity>? = null
    override fun getViewBinding() = ActivityPaymentDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.payments)

        serviceCode = intent.getStringExtra("code")!!
        serviceActionCode = intent.getStringExtra("service_action_code")!!
        number = intent.getStringExtra("number")!!
        paymentViewModel.getPaymentDetails(serviceCode, serviceActionCode, number)
        paymentViewModel.payment.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.meganeqabty.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.meganeqabty.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE

                        if (resource.data?.receipt == null) {
                            binding.cardLayout.visibility = View.GONE
                            binding.tvDetails.visibility = View.GONE
                            binding.tvPaymentMethod.visibility = View.GONE
                            binding.rgPaymentMechanismType.visibility = View.GONE
                            binding.btnNext.visibility = View.GONE
                            showDialog()
                        } else {
                            binding.llContent.visibility = View.VISIBLE
                            binding.tvService.text = resource.data.service.name
                            binding.tvName.text = "الاسم : ${resource.data.member.name}"
                            binding.tvMemberNumber.text =
                                "رقم العضوية : ${intent.getStringExtra("number")!!}"
                            totalAmount = resource.data.receipt.details.totalPrice.toInt()
                            paymentFees = resource.data.receipt.cardFees.toInt()
                            deliveryFees = resource.data.deliveryMethodsEntity[0].price.toDouble().toInt()
                            updateTotal()
                            binding.lastFeeYearValue.text =
                                "${resource.data.receipt.details.lastFeeYear} "
                            binding.currentFeeYearValue.text =
                                "${resource.data.receipt.details.currentFeeYear} ج.م "
                            binding.cardPriceValue.text =
                                "${resource.data.receipt.details.cardPrice} ج.م "
                            binding.lateSubscriptionsValue.text =
                                "${resource.data.receipt.details.lateSubscriptions} ج.م "
                            binding.delayFineValue.text =
                                "${resource.data.receipt.details.delayFine} ج.م "
                            binding.totalValue.text =
                                "${resource.data.receipt.details.totalPrice} ج.م "
                        }
                    }
                    com.neqabty.meganeqabty.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.message.toString() == "400"){
                            showAlertDialog()
                        }
                    }
                }
            }

        }

        paymentViewModel.getPaymentMethods()
        paymentViewModel.paymentMethods.observe(this) { it ->

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.meganeqabty.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.meganeqabty.core.utils.Status.SUCCESS -> {
                        if (resource.data!!.isNotEmpty()) {
                            if (resource.data.filter { it.name == "card" }[0].isActive)
                                binding.rbCard.visibility = View.VISIBLE
                            if (resource.data.filter { it.name == "code" }[0].isActive)
                                binding.rbChannel.visibility = View.VISIBLE
                            //TODO Check this before publishing
//                            if(resource.data.filter { it.name == "fawry" }[0].isActive)
//                                binding.rbFawry.visibility = View.VISIBLE
                            if (resource.data.filter { it.name == "wallet" }[0].isActive)
                                binding.rbMobileWallet.visibility = View.VISIBLE
                        }
                    }
                    com.neqabty.meganeqabty.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        binding.spBranches.adapter = branchesAdapter
        binding.spBranches.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (branchesList != null && i != 0) {
                    entityBranch = branchesList?.get(i - 1)!!.entity!!.id
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        paymentViewModel.getBranches()
        paymentViewModel.branches.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.meganeqabty.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.meganeqabty.core.utils.Status.SUCCESS -> {
                        branchesList = resource.data
                        branchesAdapter.submitList(
                            resource.data!!.toMutableList()
                                .also { list ->
                                    list.add(
                                        0,
                                        BranchesEntity(
                                            address = "اختر الفرع",
                                            entity = null,
                                            city = ""
                                        )
                                    )
                                })
                    }
                    com.neqabty.meganeqabty.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        }

        binding.rbHome.setOnClickListener {
            deliveryMethod = 1
            binding.spinnerContainer.visibility = View.GONE
            binding.address.visibility = View.VISIBLE
            binding.spBranches.setSelection(0)
            deliveryFees = paymentViewModel.payment.value?.data!!.deliveryMethodsEntity[0].price.toDouble().toInt()
            updateTotal()
        }

        binding.rbBranches.setOnClickListener {
            deliveryMethod = 2
            binding.address.visibility = View.GONE
            binding.spinnerContainer.visibility = View.VISIBLE
            deliveryFees = paymentViewModel.payment.value?.data!!.deliveryMethodsEntity[1].price.toDouble().toInt()
            updateTotal()
        }

            paymentViewModel.paymentInfo.observe(this) {

                it?.let { resource ->
                    when (resource.status) {
                        com.neqabty.meganeqabty.core.utils.Status.LOADING -> {
                            binding.progressCircular.visibility = View.VISIBLE
                        }
                        com.neqabty.meganeqabty.core.utils.Status.SUCCESS -> {
                            binding.progressCircular.visibility = View.GONE
                            if (resource.data?.payment?.transaction?.paymentGatewayReferenceId.isNullOrEmpty()) {
                                val paymentObject = resource.data as PaymentEntity
                                oPayPayment(paymentObject, true)
                            } else {
                                showAlertDialog(resource.data?.payment?.transaction?.paymentGatewayReferenceId!!)
                            }
                        }
                        com.neqabty.meganeqabty.core.utils.Status.ERROR -> {
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
                    paymentFees = paymentViewModel.payment.value?.data?.receipt?.cardFees!!.toInt()
                    binding.ivCard.visibility = View.VISIBLE
                    binding.llChannels.visibility = View.GONE
                    binding.ivFawry.visibility = View.GONE
                    updateTotal()
                }
            }
            binding.rbChannel.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    paymentMethod = "code"
                    paymentFees = paymentViewModel.payment.value?.data?.receipt?.codeFees!!.toInt()
                    binding.llChannels.visibility = View.VISIBLE
                    binding.ivCard.visibility = View.GONE
                    binding.ivFawry.visibility = View.GONE
                    updateTotal()
                }
            }
            binding.rbFawry.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    paymentMethod = "fawry"
                    paymentFees = paymentViewModel.payment.value?.data?.receipt?.codeFees!!.toInt()
                    binding.ivFawry.visibility = View.VISIBLE
                    binding.llChannels.visibility = View.GONE
                    binding.ivCard.visibility = View.GONE
                    updateTotal()
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
                if (deliveryMethod == 1){
                    address = binding.address.text.toString()
                }

                if (deliveryMethod == 1 && address.isEmpty()){
                    Toast.makeText(this, "من فضلك ادخل العنوان", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                if (deliveryMethod == 2 && binding.spBranches.selectedItemPosition == 0){
                    Toast.makeText(this, "من فضلك اختر العنوان", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                if (deliveryMethod == 2){
                    address = ""
                }


                binding.btnNext.isEnabled = false
                paymentViewModel.getPaymentInfo(
                    PaymentBody(
                        PaymentBodyObject(
                            serviceCode = serviceCode,
                            serviceActionCode = serviceActionCode,
                            paymentMethod = paymentMethod,
                            amount = "${totalAmount + paymentFees + deliveryFees}",
                            membershipId = number.toInt(),
                            address = address,
                            entityBranch = entityBranch,
                            deliveryMethod = deliveryMethod
                        )
                    )
                )
            }
        }

    private fun showAlertDialog() {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage("نأسف لعدم اتمام السداد وذلك بسبب انتهاء صلاحية ترخيص الوزارة الخاص بكم برجاء التجديد حتى يتم استكمال عملية السداد")
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "موافقة"
        ) { dialog, _ ->
            val intent = Intent(this, UpdateInfoActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
            finish()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, "لا"
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        alertDialog.show()

    }


    private fun updateTotal(){
        binding.tvAmount.text = "الاجمالى بعد ضريبة الدفع : ${totalAmount + paymentFees}"
        binding.tvAmountAfterDelivery.text =  "الاجمالى بعد ضريبة التوصيل : ${totalAmount + paymentFees + deliveryFees}"
    }

        private fun showDialog() {

            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle(getString(R.string.alert))
            alertDialog.setMessage("لا توجد اي فواتير مستحقه للسداد في الوقت الحالي")
            alertDialog.setCancelable(false)
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
            val clipboard: ClipboardManager =
                getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
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
            if (requestCode == PaymentTask.REQUEST_PAYMENT) {
                if (resultCode == PaymentTask.RESULT_PAYMENT) {
                    val response =
                        data?.getSerializableExtra(PaymentTask.RESPONSE_DATA) as WebJsResponse?
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
                        showAlert(
                            if (binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card) getString(
                                R.string.payment_successful
                            ) + PaymentGatewayReferenceId else getString(R.string.payment_reference) + PaymentGatewayReferenceId
                        ) {
//                        navController().popBackStack()
//                        navController().navigate(R.id.homeFragment)
                        }
                    }
                }
            }
        }

        private fun oPayPayment(paymentEntity: PaymentEntity, isCredit: Boolean) {
            referenceCode = paymentEntity.mobilePaymentPayload.reference
            val paymentType = if (isCredit) "BankCard" else "ReferenceCode"
            PaymentTask.sandBox = Constants.OPAY_MODE
            val payInput = PayInput(
                publickey = paymentEntity.mobilePaymentPayload.publickey,
                merchantId = paymentEntity.mobilePaymentPayload.merchantId,
                merchantName = paymentEntity.mobilePaymentPayload.merchantName,
                reference = paymentEntity.mobilePaymentPayload.reference,
                countryCode = paymentEntity.mobilePaymentPayload.countryCode, // uppercase
                currency = paymentEntity.mobilePaymentPayload.currency, // uppercase
                payAmount = (paymentEntity.mobilePaymentPayload.payAmount.toDouble() * 100).toLong(),
                productName = binding.tvService.text.toString(),
                productDescription = binding.tvService.text.toString(),
                callbackUrl = paymentEntity.mobilePaymentPayload.callbackUrl,
                userClientIP = "110.246.160.183",
                expireAt = paymentEntity.mobilePaymentPayload.expireAt,
                paymentType = paymentType // optional
            )

//        llSuperProgressbar.visibility = View.VISIBLE
            PaymentTask(this).createOrder(payInput, callback = { status, response ->
//            llSuperProgressbar.visibility = View.GONE
                when (status) {
                    Status.ERROR -> { // error
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

                    if (binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card) {
                        val intent = Intent(this, PaymentStatusActivity::class.java)
                        intent.putExtra("referenceCode", referenceCode)
                        startActivity(intent)
                        finish()
                    } else {
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