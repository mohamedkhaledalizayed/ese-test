package com.neqabty.healthcare.mega.payment.view.paymentdetails

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.otp.view.VerifyPhoneActivity
import com.neqabty.healthcare.core.data.Constants.SANDBOX
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPaymentDetailsBinding
import com.neqabty.healthcare.mega.payment.data.model.*
import com.neqabty.healthcare.mega.payment.domain.entity.branches.BranchesEntity
import com.neqabty.healthcare.mega.payment.domain.entity.payment.PaymentEntity
import com.neqabty.healthcare.mega.payment.view.PaymentViewModel
import com.neqabty.healthcare.mega.payment.view.paymentstatus.PaymentStatusActivity
import com.neqabty.healthcare.commen.profile.view.update.UpdateInfoActivity
import com.neqabty.healthcare.core.data.Constants.TOGAREEN_CODE
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import dagger.hilt.android.AndroidEntryPoint
import team.opay.business.cashier.sdk.api.*
import team.opay.business.cashier.sdk.pay.PaymentTask


@AndroidEntryPoint
class PaymentDetailsActivity : BaseActivity<ActivityPaymentDetailsBinding>(),
    CallbackPaymentInterface {

    private val paymentViewModel: PaymentViewModel by viewModels()
    private var paymentMethod = "card"
    private var serviceCode = ""
    private var serviceActionCode = ""
    private var totalAmount = 0
    private var deliveryFees = 0
    private var paymentFees = 0
    private val branchesAdapter = BranchesAdapter()
    private var deliveryMethod = 1
    private var deliveryMethodHomeId = 0
    private var deliveryMethodBranchId = 0
    private var deliveryMethodHomePrice = 0
    private var deliveryMethodBranchPrice = 0
    private var address = ""
    private var entityBranch = 1
    private var branchesList: List<BranchesEntity>? = null
    override fun getViewBinding() = ActivityPaymentDetailsBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.payments)

        binding.address.customSelectionActionModeCallback = actionMode

        if (sharedPreferences.code == TOGAREEN_CODE){
            binding.tvDetails.visibility = View.GONE
            binding.cardLayout.visibility = View.GONE
            binding.tvDeliveryMethod.visibility = View.GONE
            binding.rgDeliveryMethods.visibility = View.GONE
            binding.deliveryFees.visibility = View.GONE
            binding.deliveryFeesValue.visibility = View.GONE
        }

        serviceCode = intent.getStringExtra("code")!!
        serviceActionCode = intent.getStringExtra("service_action_code")!!
        paymentViewModel.getPaymentDetails(serviceCode, serviceActionCode, sharedPreferences.membershipId)
        paymentViewModel.payment.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.healthcare.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.healthcare.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE

                        if (resource.data?.receipt == null){
                            showDialog(getString(R.string.no_reciept))
                        }else{
                            binding.llContent.visibility = View.VISIBLE
                            binding.tvService.text = resource.data.service.name
                            binding.tvName.text =
                                resources.getString(
                                    R.string.member_name,
                                    resource.data!!.member.name ?: ""
                                )
                            binding.tvMemberNumber.text =
                                resources.getString(
                                    R.string.member_id,
                                    sharedPreferences.membershipId
                                )

                            totalAmount = resource.data.receipt.details.totalPrice.toInt()
                            deliveryMethodHomeId = 1
                            deliveryMethodBranchId = 2

                            deliveryMethodHomePrice = 1
                            deliveryMethodBranchPrice = 2


                            paymentFees = resource.data.receipt.cardFees.toInt()
                            deliveryMethod = deliveryMethodHomeId
                            deliveryFees = deliveryMethodHomePrice
                            updateTotal()
                            binding.lastFeeYearValue.text =
                                resource.data.receipt.details.lastFeeYear.toString()
                            binding.currentFeeYearValue.text =
                                resource.data.receipt.details.currentFeeYear.toString() +
                                        "  " + resources.getString(R.string.egp)
                            binding.cardPriceValue.text =
                                resource.data.receipt.details.cardPrice.toString() +
                                        "  " + resources.getString(R.string.egp)
                            binding.lateSubscriptionsValue.text =
                                resource.data.receipt.details.lateSubscriptions.toString() +
                                        "  " + resources.getString(R.string.egp)
                            binding.delayFineValue.text =
                                resource.data.receipt.details.delayFine.toString() +
                                        "  " + resources.getString(R.string.egp)
                            binding.totalValue.text =
                                resource.data.receipt.details.totalPrice.toString() +
                                        "  " + resources.getString(R.string.egp)


                            if (resource.data.gatewaysData.isNotEmpty()) {
                                if (resource.data.gatewaysData.filter { it.name == "card" }[0].isActive)
                                    binding.rbCard.visibility = View.VISIBLE
                                if (resource.data.gatewaysData.filter { it.name == "code" }[0].isActive)
                                    binding.rbChannel.visibility = View.VISIBLE
                                if (resource.data.gatewaysData.filter { it.name == "wallet" }[0].isActive)
                                    binding.rbWallet.visibility = View.VISIBLE
                            }
                        }

                    }
                    com.neqabty.healthcare.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.message.toString().split("#")[0].trim() == "3ak") {

                            val error = Gson().fromJson(resource.message.toString().split("#")[1].trim(), ErrorBody::class.java)

                            when (error.error_key) {
                                4 -> {
                                    showAlertDialogLicence(error.error)
                                }
                                7 -> {
                                    showAlertDialogLicence(error.error)
                                }
                                else -> {
                                    showDialog(error.error)
                                }
                            }

                        }
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
                    com.neqabty.healthcare.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.healthcare.core.utils.Status.SUCCESS -> {
                        branchesList = resource.data
                        branchesAdapter.submitList(
                            resource.data!!.toMutableList()
                                .also { list ->
                                    list.add(
                                        0,
                                        BranchesEntity(
                                            address = "",
                                            entity = null,
                                            city = resources.getString(R.string.select_branch)
                                        )
                                    )
                                })
                    }
                    com.neqabty.healthcare.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        }

        binding.rbHome.setOnClickListener {
            deliveryMethod = deliveryMethodHomeId
            binding.spinnerContainer.visibility = View.GONE
            binding.address.visibility = View.VISIBLE
            binding.spBranches.setSelection(0)
            deliveryFees = deliveryMethodHomePrice
            updateTotal()
        }

        binding.rbBranches.setOnClickListener {
            deliveryMethod = deliveryMethodBranchId
            binding.address.visibility = View.GONE
            binding.spinnerContainer.visibility = View.VISIBLE
            deliveryFees = deliveryMethodBranchPrice
            updateTotal()
        }

        paymentViewModel.paymentInfo.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.healthcare.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.healthcare.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data?.payment?.paymentMethod == "card") {
                            val paymentObject = resource.data as PaymentEntity
                            oPayPayment(paymentObject, true)

//                            val configData = generatePaytabsConfigurationDetails(paymentObject)
//                            PaymentSdkActivity.startCardPayment(
//                                this,
//                                configData,
//                                callback = this
//                            )
                        } else {
                            showAlertDialog(resource.data?.payment?.transaction?.paymentGatewayReferenceId!!)
                        }
                    }
                    com.neqabty.healthcare.core.utils.Status.ERROR -> {
                        binding.btnNext.isEnabled = true
                        binding.progressCircular.visibility = View.GONE
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
                updateTotal()
            }
        }

        binding.rbChannel.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                paymentMethod = "code"
                paymentFees = paymentViewModel.payment.value?.data?.receipt?.codeFees!!.toInt()
                binding.llChannels.visibility = View.VISIBLE
                binding.ivCard.visibility = View.GONE
                updateTotal()
            }
        }

        binding.btnNext.setOnClickListener {

            if (deliveryMethod == deliveryMethodHomeId) {
                address = binding.address.text.toString()
            }

            if (sharedPreferences.code == TOGAREEN_CODE){
                address = "test"
            }

            if (deliveryMethod == deliveryMethodHomeId && address.isEmpty()) {
                Toast.makeText(this, resources.getString(R.string.enter_add), Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (deliveryMethod == deliveryMethodBranchId && binding.spBranches.selectedItemPosition == 0) {
                Toast.makeText(this, resources.getString(R.string.select_branch), Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (deliveryMethod == deliveryMethodBranchId) {
                address = ""
            }


            if (sharedPreferences.isPhoneVerified) {
                binding.btnNext.isEnabled = false
                if (deliveryMethod == deliveryMethodHomeId){
                       paymentViewModel.getPaymentHomeInfo(
                           PaymentHomeBody(
                               PaymentHomeBodyObject(
                                   serviceCode = serviceCode,
                                   serviceActionCode = serviceActionCode,
                                   paymentMethod = paymentMethod,
                                   membershipId = sharedPreferences.membershipId.toInt(),
                                   address = address,
                                   deliveryMethod = deliveryMethodHomeId
                               )
                           )
                       )
                }else{
                    paymentViewModel.getPaymentInfo(
                        PaymentBody(
                            PaymentBodyObject(
                                serviceCode = serviceCode,
                                serviceActionCode = serviceActionCode,
                                paymentMethod = paymentMethod,
                                membershipId = sharedPreferences.membershipId.toInt(),
                                entityBranch = entityBranch,
                                deliveryMethod = deliveryMethodBranchId
                            )
                        )
                    )
                }

            } else {
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

    private fun showAlertDialogLicence(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.agree)
        ) { dialog, _ ->
            val intent = Intent(this, UpdateInfoActivity::class.java)
            intent.putExtra("key", 100)
            startActivity(intent)
            dialog.dismiss()
            finish()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        alertDialog.show()

    }

    private fun updateTotal() {
        binding.paymentFeesValue.text = "$paymentFees  ${resources.getString(R.string.egp)}"
        binding.deliveryFeesValue.text = "$deliveryFees  ${resources.getString(R.string.egp)}"
        if (sharedPreferences.code == TOGAREEN_CODE){
            binding.totValue.text =
                "${(totalAmount + paymentFees )}  ${resources.getString(R.string.egp)}"
        }else{
            binding.totValue.text =
                "${(totalAmount + paymentFees + deliveryFees)}  ${resources.getString(R.string.egp)}"
        }
    }

    private fun showDialog(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.agree)
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

    private fun oPayPayment(paymentEntity: PaymentEntity, isCredit: Boolean) {
        referenceCode = paymentEntity.mobilePaymentPayload!!.reference
        val paymentType = if (isCredit) "BankCard" else "ReferenceCode"
        PaymentTask.sandBox = SANDBOX
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

    var referenceCode = ""
    private fun handlePaymentResponse(response: WebJsResponse?) {
        binding.btnNext.isEnabled = true
        when (response?.orderStatus) {
            PaymentStatus.INITIAL -> {
            }
            PaymentStatus.PENDING -> {
                if (response.eventName.equals("clickResultOKBtn") && binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_channel)
                    showAlert(getString(R.string.payment_reference_without_code)) {}
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
            }
            PaymentStatus.FAIL -> {
                showAlert(getString(R.string.payment_canceled)) {}
            }
            PaymentStatus.CLOSE -> {
                showAlert(getString(R.string.payment_canceled)) {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.btnNext.isEnabled = true
    }

    //region Paytabs///
    private fun generatePaytabsConfigurationDetails(paymentEntity: PaymentEntity): PaymentSdkConfigurationDetails {
        val profileId = "103411"
        val serverKey = "SKJN6BDTKH-JG26L9WRDL-DNMN2ZZ9RN"
        val clientKey = "C7KMVN-NV2B6T-MGBPVR-2G72KG"
        val locale = PaymentSdkLanguageCode.EN /*Or PaymentSdkLanguageCode.AR*/
        val currency = "EGP"
        val merchantCountryCode = "EG"

        val billingData = PaymentSdkBillingDetails(
            "Giza",
            "eg",
            "customer@customer.com",
            "",
            sharedPreferences.mobile, "Egypt",
            binding.address.text.toString(), "132"
        )
        val configData = PaymentSdkConfigBuilder(
            profileId,
            serverKey,
            clientKey,
            (totalAmount + paymentFees + deliveryFees).toDouble(),
            currency
        )
            .setCartDescription(binding.tvService.text.toString())
            .setLanguageCode(locale)
            .setMerchantIcon(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.paytabs_logo
                )
            )
            .setBillingData(billingData)
            .setMerchantCountryCode(merchantCountryCode)
            .setTransactionType(PaymentSdkTransactionType.SALE)
            .setTransactionClass(PaymentSdkTransactionClass.ECOM)
            .setTokenise(PaymentSdkTokenise.NONE) //Check other tokenizing types in PaymentSdkTokenise
            .setCartId(paymentEntity.mobilePaymentPayload?.reference)
            .showBillingInfo(false)
            .showShippingInfo(false)
            .forceShippingInfo(false)
            .setScreenTitle(binding.tvService.text.toString())

        return configData.build()
    }

    override fun onError(error: PaymentSdkError) {
        showAlert(getString(R.string.payment_canceled)) {
            finish()
        }
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {
        showAlert(
            if (binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card || binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_mobileWallet) getString(
                R.string.payment_successful
            ) + paymentSdkTransactionDetails.paymentResult?.responseCode
            else getString(R.string.payment_reference) + paymentSdkTransactionDetails.paymentResult?.responseCode
        ) {
            val intent = Intent(this, PaymentStatusActivity::class.java)
            intent.putExtra("referenceCode", referenceCode)
            startActivity(intent)
            finish()
        }
    }

    override fun onPaymentCancel() {
        showAlert(getString(R.string.payment_canceled)) {
            finish()
        }
    }
    //endregion
}