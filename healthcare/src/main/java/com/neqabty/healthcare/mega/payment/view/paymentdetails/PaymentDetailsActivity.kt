package com.neqabty.healthcare.mega.payment.view.paymentdetails

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import com.google.gson.Gson
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.otp.view.VerifyPhoneActivity
import com.neqabty.healthcare.commen.profile.view.update.UpdateInfoActivity
import com.neqabty.healthcare.core.data.Constants.AGRI_CODE
import com.neqabty.healthcare.core.data.Constants.MORSHEDIN_CODE
import com.neqabty.healthcare.core.data.Constants.NATURAL_THERAPY_CODE
import com.neqabty.healthcare.core.data.Constants.SANDBOX
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPaymentDetailsBinding
import com.neqabty.healthcare.mega.payment.data.model.*
import com.neqabty.healthcare.mega.payment.data.model.inquiryresponse.DeliveryMethod
import com.neqabty.healthcare.mega.payment.data.model.inquiryresponse.GatewaysData
import com.neqabty.healthcare.mega.payment.domain.entity.branches.BranchesEntity
import com.neqabty.healthcare.mega.payment.domain.entity.payment.PaymentEntity
import com.neqabty.healthcare.mega.payment.view.PaymentViewModel
import com.neqabty.healthcare.mega.payment.view.paymentstatus.PaymentStatusActivity
import com.payment.paymentsdk.integrationmodels.*
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import dagger.hilt.android.AndroidEntryPoint
import team.opay.business.cashier.sdk.api.*
import team.opay.business.cashier.sdk.pay.PaymentTask


@AndroidEntryPoint
class PaymentDetailsActivity : BaseActivity<ActivityPaymentDetailsBinding>(),
    CallbackPaymentInterface {

    private val paymentViewModel: PaymentViewModel by viewModels()
    private var paymentMethod = ""
    private var serviceCode = ""
    private var serviceActionCode = ""
    private var totalAmount = 0
    private var deliveryFees = 0.0
    private var paymentFees = 0
    private val branchesAdapter = BranchesAdapter()
    private var deliveryMethod = 0
    private var deliveryMethodHomeId = 0
    private var deliveryMethodBranchId = 0
    private var deliveryMethodHomePrice = 0.0
    private var deliveryMethodBranchPrice = 0.0
    private var address = ""
    private var entityBranch = ""
    private var branchesList: List<BranchesEntity>? = null
    private var deliveryMethodsEnabled = false
    override fun getViewBinding() = ActivityPaymentDetailsBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.payments)

        binding.address.customSelectionActionModeCallback = actionMode

        serviceCode = intent.getStringExtra("code")!!
        serviceActionCode = intent.getStringExtra("service_action_code")!!
        if (sharedPreferences.code == NATURAL_THERAPY_CODE) {
            binding.rbBranches.visibility = View.GONE
            paymentViewModel.getPaymentDetails(serviceCode, serviceActionCode, "12345678")
        } else {
            paymentViewModel.getPaymentDetails(
                serviceCode,
                serviceActionCode,
                sharedPreferences.membershipId
            )
        }

        if (sharedPreferences.code != MORSHEDIN_CODE) {
            binding.tvDetails.visibility = View.GONE
            binding.cardLayout.visibility = View.GONE
        }

        paymentViewModel.payment.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.healthcare.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.healthcare.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE

                        if (resource.data?.receipt == null) {
                            showDialog(getString(R.string.no_reciept))
                        } else if (!resource.data.receipt.status!!) {
                            Toast.makeText(
                                this@PaymentDetailsActivity,
                                "${resource.data.receipt.error}",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            createRadioButton(resource.data.gatewaysData)
                            handleDeliveryMethods(resource.data.deliveryMethods)
                            binding.llContent.visibility = View.VISIBLE
                            binding.tvService.text = resource.data.service.name
                            binding.tvName.text =
                                resources.getString(
                                    R.string.member_name,
                                    resource.data.member.name ?: ""
                                )
                            binding.tvMemberNumber.text =
                                resources.getString(
                                    R.string.member_id,
                                    sharedPreferences.membershipId
                                )

                            binding.lastFeeYearValue.text =
                                resource.data.receipt.lastFeeYear.toString()
                            binding.currentFeeYearValue.text =
                                resource.data.receipt.currentFeeYear.toString() +
                                        "  " + resources.getString(R.string.egp)
                            binding.cardPriceValue.text =
                                resource.data.receipt.cardPrice.toString() +
                                        "  " + resources.getString(R.string.egp)
                            binding.lateSubscriptionsValue.text =
                                resource.data.receipt.lateSubscriptions.toString() +
                                        "  " + resources.getString(R.string.egp)
                            binding.delayFineValue.text =
                                resource.data.receipt.delayFine.toString() +
                                        "  " + resources.getString(R.string.egp)
                            binding.totalValue.text =
                                resource.data.receipt.netAmount.toString() +
                                        "  " + resources.getString(R.string.egp)
                            binding.totValue.text = "${resource.data.receipt.netAmount}  ${resources.getString(R.string.egp)}"
                        }

                    }
                    com.neqabty.healthcare.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.message.toString().split("#")[0].trim() == "3ak") {

                            val error = Gson().fromJson(
                                resource.message.toString().split("#")[1].trim(),
                                ErrorBody::class.java
                            )

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
                    entityBranch = branchesList?.get(i - 1)!!.id
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
                                            id = "",
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
                        when (resource.data?.paymentMethod) {
                            "Opay Card" -> {
                                oPayPayment(resource.data)
                            }
                            "wallet" -> {

                            }
                            "Opay Code" -> {
                                showAlertDialog(resource.data.paymentGatewayTransactionNum)
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

        binding.btnNext.setOnClickListener {

            if (paymentMethod.isEmpty()) {
                Toast.makeText(this, "من فضلك اختر طريقة الدفع.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (deliveryMethod == 0 && deliveryMethodsEnabled){
                Toast.makeText(this, "من فضلك اختر طريقة التوصيل.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (deliveryMethod == deliveryMethodHomeId) {
                address = binding.address.text.toString()
            }

            if (deliveryMethod == deliveryMethodHomeId && address.isEmpty() && deliveryMethodsEnabled) {
                Toast.makeText(this, resources.getString(R.string.enter_add), Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (deliveryMethod == deliveryMethodBranchId && binding.spBranches.selectedItemPosition == 0 && sharedPreferences.code != AGRI_CODE) {
                Toast.makeText(this, resources.getString(R.string.select_branch), Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (sharedPreferences.isPhoneVerified) {
                binding.btnNext.isEnabled = false

                when (sharedPreferences.code) {
                    NATURAL_THERAPY_CODE -> {
                        paymentViewModel.getPaymentInfo(
                            PaymentNaturalBody(
                                serviceCode = serviceCode,
                                serviceActionCode = serviceActionCode,
                                paymentMethod = paymentMethod,
                                address = address,
                                deliveryMethod = if (deliveryMethodsEnabled) deliveryMethod else null,
                                deliveryMobile = binding.mobile.text.toString(),
                                deliveryNotes = binding.notes.text.toString()
                            )
                        )
                    }
                    AGRI_CODE -> {
                        paymentViewModel.getPaymentInfo(
                            PaymentAgriBody(
                                serviceCode = serviceCode,
                                serviceActionCode = serviceActionCode,
                                paymentMethod = paymentMethod,
                                deliveryMethod = if (deliveryMethodsEnabled) deliveryMethod else null,
                                membershipId = sharedPreferences.membershipId.toInt(),
                                deliveryMobile = binding.mobile.text.toString(),
                                deliveryNotes = binding.notes.text.toString()
                            )
                        )
                    }
                    MORSHEDIN_CODE -> {
                        if (deliveryMethod == deliveryMethodHomeId) {
                            paymentViewModel.getPaymentInfo(
                                PaymentMorshedinBody(
                                    serviceCode = serviceCode,
                                    serviceActionCode = serviceActionCode,
                                    paymentMethod = paymentMethod,
                                    address = address,
                                    membershipId = sharedPreferences.membershipId.toInt(),
                                    deliveryMethod = deliveryMethodHomeId,
                                    deliveryMobile = binding.mobile.text.toString(),
                                    deliveryNotes = binding.notes.text.toString()
                                )
                            )
                        } else {
                            paymentViewModel.getPaymentInfo(
                                PaymentMorshedinBody(
                                    serviceCode = serviceCode,
                                    serviceActionCode = serviceActionCode,
                                    paymentMethod = paymentMethod,
                                    membershipId = sharedPreferences.membershipId.toInt(),
                                    branch = entityBranch,
                                    deliveryMethod = deliveryMethodBranchId,
                                    deliveryMobile = binding.mobile.text.toString(),
                                    deliveryNotes = binding.notes.text.toString()
                                )
                            )
                        }

                    }
                }

            } else {
                verifyPhone()
            }
        }
    }

    private fun handleDeliveryMethods(deliveryMethods: List<DeliveryMethod>) {

        if (deliveryMethods.isEmpty()) {
            binding.deliveryFees.visibility = View.GONE
            binding.deliveryFeesValue.visibility = View.GONE
//            binding.fees.visibility = View.GONE
            return
        }

        deliveryMethodsEnabled = true
        if (deliveryMethods.any { it.type == "Home" }) {
            setDeliveryViewsVisible()
            binding.rbHome.visibility = View.VISIBLE
            deliveryMethodHomeId = deliveryMethods.filter { it.type == "Home" }[0].id
            deliveryMethodHomePrice = deliveryMethods.filter { it.type == "Home" }[0].price
        }

        if (deliveryMethods.any { it.type == "Branch" }) {
            setDeliveryViewsVisible()
            binding.rbBranches.visibility = View.VISIBLE
            deliveryMethodBranchId = deliveryMethods.filter { it.type == "Branch" }[0].id
            deliveryMethodBranchPrice = deliveryMethods.filter { it.type == "Branch" }[0].price
        }

    }

    private fun setDeliveryViewsVisible() {
        binding.tvDeliveryMethod.visibility = View.VISIBLE
        binding.deliveryInfo.visibility = View.VISIBLE
        binding.mobile.visibility = View.VISIBLE
    }

    private fun createRadioButton(gatewaysData: List<GatewaysData>) {
        val rg = RadioGroup(this)
        rg.orientation = RadioGroup.VERTICAL
        for (item in gatewaysData) {
            var rb = RadioButton(this)
            rb.text = item.displayName
            rb.id = item.id
            rb.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    paymentMethod = item.name
                    when (paymentMethod) {
                        "Opay Card" -> {
                            binding.paymentLogo.visibility = View.VISIBLE
                            binding.paymentLogo.setImageResource(R.drawable.visa)
                        }
                        "Opay Code" -> {
                            binding.paymentLogo.visibility = View.VISIBLE
                            binding.paymentLogo.setImageResource(R.drawable.opay)
                        }
                        else -> {
                            binding.paymentLogo.visibility = View.GONE
                        }
                    }
                    totalAmount = paymentViewModel.payment.value?.data?.receipt?.total_price!!
                        .filter { it.gateway == item.name }[0].price.toInt()
                    paymentFees = paymentViewModel.payment.value?.data?.receipt?.total_price!!
                        .filter { it.gateway == item.name }[0].fees.toInt()
                    updateTotal()
                }
            }
            rb.setPadding(20, 20, 20, 20)
            rg.addView(rb)
        }
        binding.llMainLayout.addView(rg)
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

//    private fun updateTotal() {
//        if (sharedPreferences.code == AGRI_CODE) {
//            binding.paymentFeesValue.text = "$paymentFees  ${resources.getString(R.string.egp)}"
//            binding.totValue.text = "$totalAmount  ${resources.getString(R.string.egp)}"
//        } else {
//            binding.paymentFeesValue.text = "$paymentFees  ${resources.getString(R.string.egp)}"
//            binding.deliveryFeesValue.text = "$deliveryFees  ${resources.getString(R.string.egp)}"
//            binding.totValue.text =
//                "${(totalAmount + deliveryFees)}  ${resources.getString(R.string.egp)}"
//        }
//    }

    private fun updateTotal() {
        if (sharedPreferences.code == MORSHEDIN_CODE) {
            binding.totValue.text = "${(totalAmount + deliveryFees)}  ${resources.getString(R.string.egp)}"
        }else{
            binding.totValue.text = "$totalAmount  ${resources.getString(R.string.egp)}"
        }

        binding.paymentFeesValue.text = "$paymentFees  ${resources.getString(R.string.egp)}"
        binding.deliveryFeesValue.text = "$deliveryFees  ${resources.getString(R.string.egp)} يتم تحصيلها عند الاستلام"

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

    private fun oPayPayment(paymentEntity: PaymentEntity) {
        referenceCode = paymentEntity.id
        PaymentTask.sandBox = SANDBOX
        val payInput = PayInput(
            publickey = paymentEntity.publicKey,
            merchantId = paymentEntity.merchantId,
            merchantName = "Neqabty",
            reference = paymentEntity.paymentGatewayTransactionNum,
            countryCode = "EG", // uppercase
            currency = "EGP", // uppercase
            payAmount = (paymentEntity.totalAmount.toDouble() * 100).toLong(),
            productName = binding.tvService.text.toString(),
            productDescription = binding.tvService.text.toString(),
            callbackUrl = paymentEntity.callBackURL,
            userClientIP = "110.246.160.183",
            expireAt = paymentEntity.expireAt.toInt(),
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

    var referenceCode = ""
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