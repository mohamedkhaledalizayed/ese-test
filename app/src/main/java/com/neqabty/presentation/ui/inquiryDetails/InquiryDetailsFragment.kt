package com.neqabty.presentation.ui.inquiryDetails

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.InquiryDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.PaymentRequestUI
import com.neqabty.presentation.entities.RenewalPaymentUI
import com.neqabty.presentation.util.autoCleared
import com.payment.paymentsdk.PaymentSdkActivity.Companion.startCardPayment
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.inquiry_details_fragment.*
import me.cowpay.PaymentMethodsActivity
import me.cowpay.util.CowpayConstantKeys
import team.opay.business.cashier.sdk.api.*
import team.opay.business.cashier.sdk.pay.PaymentTask
import javax.inject.Inject

@AndroidEntryPoint
class InquiryDetailsFragment : BaseFragment(), CallbackPaymentInterface {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var adapter by autoCleared<RenewPaymentItemsAdapter>()

    private val inquiryDetailsViewModel: InquiryDetailsViewModel by viewModels()

    var binding by autoCleared<InquiryDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var renewalPayment: RenewalPaymentUI
    lateinit var paymentRequestUI: PaymentRequestUI

    lateinit var mechanismTypeButton: RadioButton

    lateinit var params: InquiryDetailsFragmentArgs
    var sendDecryptionKey = false
    var title = ""
//    var commission: Double = 0.0
    var newAmount: Float = 0.0F
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.inquiry_details_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        inquiryDetailsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        inquiryDetailsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                addRenewalRequest()
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        params = InquiryDetailsFragmentArgs.fromBundle(arguments!!)
        renewalPayment = params.renewalPaymentUI

        initializeViews()
    }

    fun initializeViews() {
        llContent.visibility = View.VISIBLE
        val adapter = RenewPaymentItemsAdapter(dataBindingComponent, appExecutors) { }
        this.adapter = adapter

        renewalPayment = params.renewalPaymentUI
        binding.title = params.title
        title = params.title
        binding.number = params.number

        calculateCommission(Constants.PaymentOption.OpayCredit)
        renewalPayment?.let {
            binding.renewalPayment = it
        }

        renewalPayment.paymentItem?.paymentDetailsItems?.let {
            adapter.submitList(it)
            binding.rvDetails.adapter = adapter
        }

        rb_home.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                edAddress.visibility = View.VISIBLE
                edMobile.visibility = View.VISIBLE
            }
        }
        rb_mainSyndicate.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                edAddress.visibility = View.GONE
            }
        }
        rb_syndicate.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                edAddress.visibility = View.GONE
            }
        }

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
            if ((rb_home.isChecked && edAddress.text.toString().isBlank())) {
                showAlert(getString(R.string.invalid_data))
                return@setOnClickListener
            }else if (!isDataValid(edMobile.text.toString()))
                return@setOnClickListener
            llSuperProgressbar.visibility = View.VISIBLE
//            createPayment()
            addRenewalRequest()
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
                    showAlert(if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card) getString(R.string.payment_successful) + PaymentGatewayReferenceId else getString(R.string.payment_reference) + PaymentGatewayReferenceId) {
                        navController().popBackStack()
                        navController().navigate(R.id.homeFragment)
                    }
                }
            }
        }
    }

    private fun handleViewState(state: InquiryDetailsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading) {
            state.paymentRequestUI?.let {
                paymentRequestUI = it
                if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card)
                    oPayPayment(Constants.PaymentOption.OpayCredit)
                else if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_channel)
                    oPayPayment(Constants.PaymentOption.OpayPOS)
                else if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_mobileWallet)
                    oPayPayment(Constants.PaymentOption.MobileWallet)
                else
                    cowPayPayment(false)
            state.medicalRenewalPayment?.let {
                medicalRenewalPayment = it
                when (rgPaymentMechanismType.checkedRadioButtonId) {
                    R.id.rb_card -> {
                        val configData = generatePaytabsConfigurationDetails()
                        startCardPayment(requireActivity(), configData, callback = this)
                    }
                    R.id.rb_channel -> oPayPayment(Constants.PaymentOption.OpayPOS)
                    R.id.rb_mobileWallet -> oPayPayment(Constants.PaymentOption.MobileWallet)
                    else -> cowPayPayment(false)
                }
            }
        }
    }

    //region

    fun oPayPayment(paymentOption: Constants.PaymentOption) {
        val paymentType = when(paymentOption) {
            Constants.PaymentOption.OpayCredit -> "BankCard"
            Constants.PaymentOption.OpayPOS -> "ReferenceCode"
            Constants.PaymentOption.MobileWallet -> "MWALLET"
            else -> ""
        }
        PaymentTask.sandBox = Constants.OPAY_MODE
        val userInfo = UserInfo(paymentRequestUI.netAmount.toString(), binding.number, sharedPref.mobile, renewalPayment.paymentItem?.engName)
        val payInput = PayInput(
            publickey = Constants.OPAY_PUBLIC_KEY,
            merchantId = Constants.OPAY_MERCHANT_ID,
            merchantName = Constants.OPAY_MERCHANT_NAME,
            reference = paymentRequestUI.refId,
            countryCode = "EG", // uppercase
            payAmount = (paymentRequestUI.amount?.times(100))?.toLong()!!,
            currency = "EGP", // uppercase
            productName = "annualSubscription",
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
        intent.putExtra(CowpayConstantKeys.MerchantReferenceId, paymentRequestUI.refId)
        //order price780
        intent.putExtra(CowpayConstantKeys.Amount, paymentRequestUI.amount.toString())
        //user data
        intent.putExtra(CowpayConstantKeys.Description, paymentRequestUI.netAmount.toString())
        intent.putExtra(CowpayConstantKeys.CustomerName, params.number)
        intent.putExtra(CowpayConstantKeys.CustomerMobile, sharedPref.mobile)
        intent.putExtra(CowpayConstantKeys.CustomerEmail, "customer@customer.com")
        //user id
        intent.putExtra(CowpayConstantKeys.CustomerMerchantProfileId, paymentRequestUI.refId)


        startActivityForResult(intent, CowpayConstantKeys.PaymentMethodsActivityRequestCode)
    }

    //region
    private fun calculateCommission(paymentOption: Constants.PaymentOption) {
        when (paymentOption) {
            Constants.PaymentOption.OpayCredit -> newAmount = renewalPayment.amounts?.get(1)?.cardAmount!!
            Constants.PaymentOption.OpayPOS -> newAmount = renewalPayment.amounts?.get(1)?.posAmount!!
            Constants.PaymentOption.Fawry -> newAmount = renewalPayment.amounts?.get(0)?.posAmount!!
            else -> {}
            Constants.PaymentOption.OpayCredit -> commission = if (medicalRenewalPayment.paymentItem?.amount?.times(Constants.CC_COMMISSION)!! > Constants.MIN_COMMISSION) medicalRenewalPayment.paymentItem?.amount?.times(Constants.CC_COMMISSION) as Double else Constants.MIN_COMMISSION
            Constants.PaymentOption.OpayPOS -> commission = if (medicalRenewalPayment.paymentItem?.amount?.times(Constants.POS_COMMISSION)!! > Constants.MIN_COMMISSION) medicalRenewalPayment.paymentItem?.amount?.times(Constants.POS_COMMISSION) as Double else Constants.MIN_COMMISSION
            Constants.PaymentOption.Fawry -> commission = if (medicalRenewalPayment.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION)!! > Constants.MIN_COMMISSION) medicalRenewalPayment.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION) as Double else Constants.MIN_COMMISSION
            else -> {}
        }
        commission = Math.round(commission * 10.0) / 10.0
        newAmount = (medicalRenewalPayment.paymentItem?.amount ?: 0.0) + commission
        binding.newAmount = newAmount
//        when (paymentOption) {
//            Constants.PaymentOption.OpayCredit -> commission = if (renewalPayment.paymentItem?.amount?.times(Constants.CC_COMMISSION)!! > Constants.MIN_COMMISSION) renewalPayment.paymentItem?.amount?.times(Constants.CC_COMMISSION) as Double else Constants.MIN_COMMISSION
//            Constants.PaymentOption.OpayPOS -> commission = if (renewalPayment.paymentItem?.amount?.times(Constants.POS_COMMISSION)!! > Constants.MIN_COMMISSION) renewalPayment.paymentItem?.amount?.times(Constants.POS_COMMISSION) as Double else Constants.MIN_COMMISSION
//            Constants.PaymentOption.Fawry -> commission = if (renewalPayment.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION)!! > Constants.MIN_COMMISSION) renewalPayment.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION) as Double else Constants.MIN_COMMISSION
//        }
//        commission = Math.round(commission * 10.0) / 10.0
//        newAmount = (renewalPayment.paymentItem?.amount ?: 0) + commission
//        binding.newAmount = newAmount
//        updateCommissionInList()
    }

//    private fun updateCommissionInList() {
//        renewalPayment.paymentItem?.paymentDetailsItems?.let {
//            it.let {
//                val tmp = it.toMutableList()
//                tmp.add(RenewalPaymentUI.PaymentDetailsItem(getString(R.string.commission), commission.toString()))
//                adapter.submitList(tmp)
//                rvDetails.adapter = adapter
//            }
//        }
//    }

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
                showAlert(
                    if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card || rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_mobileWallet) getString(
                        R.string.payment_successful
                    ) + response.orderNo
                    else getString(R.string.payment_reference) + response.referenceCode
                ) {
                    navController().popBackStack()
                    navController().navigate(R.id.homeFragment)
                }
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

    private fun isDataValid(mobile: String): Boolean {
        return if (mobile.matches(Regex("[0-9]*")) && mobile.trim().length == 11 && (mobile.substring(0, 3).equals("012") || mobile.substring(0, 3).equals("010") || mobile.substring(0, 3).equals("011") || mobile.substring(0, 3).equals("015")))
            true
        else {
            showAlert(getString(R.string.invalid_mobile))
            false
        }
    }

    fun addRenewalRequest(){
        inquiryDetailsViewModel.addRenewalRequest(sharedPref.mobile, params.number, renewalPayment.paymentItem?.engName ?: "", params.serviceID,
            if (rb_card.isChecked) "card" else "pos",
            if (rb_card.isChecked) 2 else if (rb_channel.isChecked) 2 else 1,
            if (rb_syndicate.isChecked) Constants.DELIVERY_LOCATION_SYNDICATE else if (rb_home.isChecked) Constants.DELIVERY_LOCATION_HOME else Constants.DELIVERY_LOCATION_MAIN_SYNDICATE,
            edAddress.text.toString(), edMobile.text.toString()
        )
    }

    private fun generatePaytabsConfigurationDetails(selectedApm: PaymentSdkApms? = null): PaymentSdkConfigurationDetails {
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
            sharedPref.mobile, "Egypt",
            binding.edAddress.text.toString(), "132"
        )

        val shippingData = PaymentSdkShippingDetails(
            "Giza",
            "eg",
            "customer@customer.com",
            params.number,
            sharedPref.mobile, "Egypt",
            binding.edAddress.text.toString(), "132"
        )

        val configData = PaymentSdkConfigBuilder(
            profileId,
            serverKey,
            clientKey,
            newAmount,
            currency
        )
            .setCartDescription(medicalRenewalPayment.paymentItem?.amount.toString())
            .setLanguageCode(locale)
            .setMerchantIcon(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.payment_sdk_adcb_logo
                )
            )
            .setBillingData(billingData)
            .setMerchantCountryCode(merchantCountryCode)
            .setTransactionType(PaymentSdkTransactionType.SALE)
            .setTransactionClass(PaymentSdkTransactionClass.ECOM)
            .setShippingData(shippingData)
            .setTokenise(PaymentSdkTokenise.USER_MANDATORY) //Check other tokenizing types in PaymentSdkTokenise
            .setCartId(medicalRenewalPayment.paymentItem?.paymentRequestNumber)
            .showBillingInfo(false)
            .showShippingInfo(false)
            .forceShippingInfo(false)
            .setScreenTitle(params.title)

        if (selectedApm != null)
            configData.setAlternativePaymentMethods(listOf(selectedApm))

        return configData.build()
    }

    override fun onError(error: PaymentSdkError) {
        showAlert(getString(R.string.payment_canceled)) {
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {
        showAlert(
            if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card || rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_mobileWallet) getString(
                R.string.payment_successful
            ) + paymentSdkTransactionDetails.paymentResult?.responseCode
            else getString(R.string.payment_reference) + paymentSdkTransactionDetails.paymentResult?.responseCode
        ) {
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }
    }

    override fun onPaymentCancel() {
        showAlert(getString(R.string.payment_canceled)) {
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }
    }
    //endregion

    fun navController() = findNavController()
}
