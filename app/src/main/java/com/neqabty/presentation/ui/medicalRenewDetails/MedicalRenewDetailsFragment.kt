package com.neqabty.presentation.ui.medicalRenewDetails

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.entities.PaymentRequestUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.medical_renew_details_fragment.*
import me.cowpay.PaymentMethodsActivity
import me.cowpay.util.CowpayConstantKeys
import team.opay.business.cashier.sdk.api.*
import team.opay.business.cashier.sdk.pay.PaymentTask
import javax.inject.Inject

@AndroidEntryPoint
class MedicalRenewDetailsFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var adapter by autoCleared<MedicalRenewPaymentItemsAdapter>()

    private val medicalRenewDetailsViewModel: MedicalRenewDetailsViewModel by viewModels()

    var binding by autoCleared<MedicalRenewDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var medicalRenewalPaymentUI: MedicalRenewalPaymentUI

    lateinit var mechanismTypeButton: RadioButton

    var sendDecryptionKey = false
    var deliveryType: Int = Constants.DELIVERY_LOCATION_HOME
    lateinit var address: String
    lateinit var mobile: String
    lateinit var medicalRenewalUI: MedicalRenewalUI
    lateinit var paymentRequestUI: PaymentRequestUI

    var commission: Double = 0.0
    var newAmount: Double = 0.0
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_renew_details_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = MedicalRenewDetailsFragmentArgs.fromBundle(arguments!!)
        deliveryType = params.deliveryType
        address = params.address
        mobile = params.mobile
        medicalRenewalUI = params.medicalRenewalUI

        medicalRenewDetailsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalRenewDetailsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                medicalRenewDetailsViewModel.paymentInquiry(sharedPref.mobile, medicalRenewalUI.oldRefId!!, deliveryType, address, mobile)
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
//        initializeViews()
        medicalRenewDetailsViewModel.paymentInquiry(sharedPref.mobile, medicalRenewalUI.oldRefId!!, deliveryType, address, mobile)
    }

    fun initializeViews() {
        llContent.visibility = View.VISIBLE
        val adapter = MedicalRenewPaymentItemsAdapter(dataBindingComponent, appExecutors) { }
        this.adapter = adapter

        calculateCommission(Constants.PaymentOption.OpayCredit)
//        medicalRenewalPaymentUI.paymentItem = MedicalRenewalPaymentUI.PaymentItem(paymentRequestNumber = "111",amount = 555,name = "mona",paymentDetailsItems = null)
        medicalRenewalPaymentUI?.let {
            binding.medicalRenewalPayment = it

            bPay.visibility = if (it.paymentItem?.amount == null || it.paymentItem?.amount == 0.0) View.GONE else View.VISIBLE
        }

        binding.rvDetails.adapter = adapter



        rb_card.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                calculateCommission(Constants.PaymentOption.OpayCredit)
                ivCard.visibility = View.VISIBLE
                llChannels.visibility = View.GONE
                ivFawry.visibility = View.GONE
            }
        }
        rb_channel.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                calculateCommission(Constants.PaymentOption.OpayPOS)
                llChannels.visibility = View.VISIBLE
                ivCard.visibility = View.GONE
                ivFawry.visibility = View.GONE
            }
        }
        rb_fawry.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                calculateCommission(Constants.PaymentOption.Fawry)
                ivFawry.visibility = View.VISIBLE
                llChannels.visibility = View.GONE
                ivCard.visibility = View.GONE
            }
        }
        rb_mobileWallet.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                calculateCommission(Constants.PaymentOption.Fawry)
                ivFawry.visibility = View.GONE
                llChannels.visibility = View.GONE
                ivCard.visibility = View.GONE
            }
        }

        tvChannels.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://cashier.opaycheckout.com/map")))
        }

        bPay.setOnClickListener {
            bPay.isEnabled = false
            proceedToPaymentConfirmation()
            bPay.isEnabled = true
        }
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
                    navController().popBackStack()
                    navController().navigate(R.id.homeFragment)
                }
            } else if (responseCode == CowpayConstantKeys.SuccessCode) {
                var responseMSG = data.extras!!.getString(CowpayConstantKeys.ResponseMessage)
                var PaymentGatewayReferenceId =
                        data.extras!!.getString(CowpayConstantKeys.PaymentGatewayReferenceId)
                responseMSG?.let {
                    if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card)
                        showAlert(getString(R.string.payment_successful) + PaymentGatewayReferenceId + getString(R.string.medical_card_delivery)){
                            navController().popBackStack()
                            navController().navigate(R.id.homeFragment)
                        }
                    else
                        showCopyAlertDialog(PaymentGatewayReferenceId!!)
                }
            }
        }
    }

    private fun handleViewState(state: MedicalRenewDetailsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading) {
            state.fawryTransactionUI?.let{
                showAlert(getString(R.string.payment_reference) + it.referenceNumber){
                    navController().popBackStack()
                    navController().navigate(R.id.homeFragment)
                }
                return
            }
            state.paymentRequestUI?.let {
                paymentRequestUI = it
                if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_fawry)
                    medicalRenewDetailsViewModel.createFawryTransaction(paymentRequestUI.refId)// cowPayPayment(false)
                return
            }
            state.medicalRenewalPayment?.let {
                medicalRenewalPaymentUI = it
                if ((state.medicalRenewalPayment as MedicalRenewalPaymentUI).resultType == "-2")
                    showAlert((state.medicalRenewalPayment as MedicalRenewalPaymentUI).msg) {
                        navController().popBackStack()
                        navController().navigate(R.id.homeFragment)
                    }
                else if ((state.medicalRenewalPayment as MedicalRenewalPaymentUI).resultType == "-1")
                    showAlert((state.medicalRenewalPayment as MedicalRenewalPaymentUI).msg) {
                        navController().popBackStack()
                        navController().navigate(R.id.homeFragment)
                    }
                else
                    initializeViews()
            }
        }
    }

    //region
    private fun showCopyAlertDialog(paymentGatewayReferenceId: String) {
        val alertDialog = android.app.AlertDialog.Builder(activity).create()
        alertDialog.setTitle(getString(R.string.alert_title))
        alertDialog.setMessage(getString(R.string.payment_reference) + paymentGatewayReferenceId + getString(R.string.medical_card_delivery))
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            android.app.AlertDialog.BUTTON_POSITIVE, getString(R.string.copy)
        ) { dialog, _ ->
            dialog.dismiss()
            copyText(paymentGatewayReferenceId)
        }
        alertDialog.show()
    }

    private fun copyText(paymentGatewayReferenceId: String) {
        val clipboard: ClipboardManager =
            activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", paymentGatewayReferenceId)
        clipboard.setPrimaryClip(clip)
    }

    fun oPayPayment(paymentOption: Constants.PaymentOption) {
        val paymentType = when(paymentOption) {
            Constants.PaymentOption.OpayCredit -> "BankCard"
            Constants.PaymentOption.OpayPOS -> "ReferenceCode"
            Constants.PaymentOption.MobileWallet -> "MWALLET"
            else -> ""
        }
        PaymentTask.sandBox = Constants.OPAY_MODE
        val userInfo = UserInfo(medicalRenewalPaymentUI.paymentItem?.amount.toString(), sharedPref.user, sharedPref.mobile, sharedPref.name)
        val payInput = PayInput(
            publickey = Constants.OPAY_PUBLIC_KEY,
            merchantId = Constants.OPAY_MERCHANT_ID,
            merchantName = Constants.OPAY_MERCHANT_NAME,
            reference = medicalRenewalPaymentUI.paymentItem?.paymentRequestNumber!!,
            countryCode = "EG", // uppercase
            payAmount = (newAmount * 100).toLong(),
            currency = "EGP", // uppercase
            productName = "healthCareSubscription",
            productDescription = "android",
            callbackUrl = Constants.OPAY_PAYMENT_CALLBACK_URL,
            paymentType = paymentType, // optional
            expireAt = if(paymentOption == Constants.PaymentOption.OpayCredit) 30 else 2880,
            userClientIP = "110.246.160.183",
            userInfo = userInfo
        )

        llSuperProgressbar.visibility = View.VISIBLE
        PaymentTask(this).createOrder(payInput, callback = { status, response ->
            llSuperProgressbar.visibility = View.GONE
            when (status) {
                Status.ERROR -> { // error
                    Toast.makeText(requireContext(), response.message,
                        Toast.LENGTH_SHORT).show()
                } else -> {
            }
            }
        })
    }

    fun cowPayPayment(isCredit: Boolean) {
        var intent = Intent(context, PaymentMethodsActivity::class.java)

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
        intent.putExtra(CowpayConstantKeys.MerchantReferenceId, medicalRenewalPaymentUI.paymentItem?.paymentRequestNumber)
        //order price780
        intent.putExtra(CowpayConstantKeys.Amount, newAmount.toString())
        //user data
        intent.putExtra(CowpayConstantKeys.Description, medicalRenewalPaymentUI.paymentItem?.amount.toString())
        intent.putExtra(CowpayConstantKeys.CustomerName, medicalRenewalUI.oldRefId)
        intent.putExtra(CowpayConstantKeys.CustomerMobile, sharedPref.mobile)
        intent.putExtra(CowpayConstantKeys.CustomerEmail, "customer@customer.com")
        //user id
        intent.putExtra(CowpayConstantKeys.CustomerMerchantProfileId, medicalRenewalPaymentUI.paymentItem?.paymentRequestNumber)


        startActivityForResult(intent, CowpayConstantKeys.PaymentMethodsActivityRequestCode)
    }

    private fun calculateCommission(paymentOption: Constants.PaymentOption) {
        when (paymentOption) {
            Constants.PaymentOption.OpayCredit -> commission = if (medicalRenewalPaymentUI.paymentItem?.amount?.times(Constants.CC_COMMISSION)!! > Constants.MIN_COMMISSION) medicalRenewalPaymentUI.paymentItem?.amount?.times(Constants.CC_COMMISSION) as Double else Constants.MIN_COMMISSION
            Constants.PaymentOption.OpayPOS -> commission = if (medicalRenewalPaymentUI.paymentItem?.amount?.times(Constants.POS_COMMISSION)!! > Constants.MIN_COMMISSION) medicalRenewalPaymentUI.paymentItem?.amount?.times(Constants.POS_COMMISSION) as Double else Constants.MIN_COMMISSION
            Constants.PaymentOption.Fawry -> commission = if (medicalRenewalPaymentUI.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION)!! > Constants.MIN_COMMISSION) medicalRenewalPaymentUI.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION) as Double else Constants.MIN_COMMISSION
        }
        commission = Math.round(commission * 10.0) / 10.0
        newAmount = (medicalRenewalPaymentUI.paymentItem?.amount ?: 0.0) + commission
        binding.newAmount = newAmount
        updateCommissionInList()
    }

    private fun updateCommissionInList() {
        medicalRenewalPaymentUI.paymentItem?.paymentDetailsItems?.let {
            it.let {
                val tmp = it.toMutableList()
                tmp.add(MedicalRenewalPaymentUI.PaymentDetailsItem(getString(R.string.commission), commission.toString()))
                adapter.submitList(tmp)
                rvDetails.adapter = adapter
            }
        }
    }

    private fun proceedToPaymentConfirmation() {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.alert_title))
        builder?.setMessage(getString(R.string.confirm_proceed_to_payment_msg))
        builder?.setPositiveButton(getString(R.string.alert_confirm)) { dialog, which ->
            llSuperProgressbar.visibility = View.VISIBLE
            if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card)
                oPayPayment(Constants.PaymentOption.OpayCredit)
            else if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_channel)
                oPayPayment(Constants.PaymentOption.OpayPOS)
            else if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_mobileWallet)
                oPayPayment(Constants.PaymentOption.MobileWallet)
            else
                addMedicalRequest()
                dialog.dismiss()
        }
        builder?.setNegativeButton(getString(R.string.cancel_btn)) { dialog, which ->
            dialog.dismiss()
        }

        var dialog = builder?.create()
        dialog?.show()
    }

    private fun handlePaymentResponse(response: WebJsResponse?) {
        when (response?.orderStatus) {
            PaymentStatus.INITIAL -> {
            }
            PaymentStatus.PENDING -> {
                if (response.eventName.equals("clickResultOKBtn") && rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_channel)
                    showAlert(getString(R.string.payment_reference_without_code)) {
                        navController().popBackStack()
                        navController().navigate(R.id.homeFragment)
                    }
            }
            PaymentStatus.SUCCESS -> {
                if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card || rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_mobileWallet)
                    showAlert(getString(R.string.payment_successful) + response.orderNo + getString(R.string.medical_card_delivery)) {
                        navController().popBackStack()
                        navController().navigate(R.id.homeFragment)
                    }
                else
                    showCopyAlertDialog(response.referenceCode!!)
            }
            PaymentStatus.FAIL -> {
                showAlert(getString(R.string.payment_canceled)) {
                    navController().popBackStack()
                    navController().navigate(R.id.homeFragment)
                }
            }
            PaymentStatus.CLOSE -> {
                showAlert(getString(R.string.payment_canceled)) {
                    navController().popBackStack()
                    navController().navigate(R.id.homeFragment)
                }
            }
        }
    }

    fun addMedicalRequest(){
        medicalRenewDetailsViewModel.addMedicalRenewalRequest(sharedPref.mobile, sharedPref.user, sharedPref.name, 1,
            if (rb_card.isChecked) "card" else "pos",
            if (rb_card.isChecked) 3 else if (rb_channel.isChecked) 2 else 4,
            deliveryType, address, mobile
        )
    }

    //endregion

    fun navController() = findNavController()
}
