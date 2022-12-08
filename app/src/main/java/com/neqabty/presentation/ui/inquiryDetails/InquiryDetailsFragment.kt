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
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.PaymentRequestUI
import com.neqabty.presentation.ui.medicalRenewDetails.MedicalRenewPaymentItemsAdapter
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.inquiry_details_fragment.*
import me.cowpay.PaymentMethodsActivity
import me.cowpay.util.CowpayConstantKeys
import team.opay.business.cashier.sdk.api.*
import team.opay.business.cashier.sdk.pay.PaymentTask
import javax.inject.Inject

@AndroidEntryPoint
class InquiryDetailsFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var adapter by autoCleared<MedicalRenewPaymentItemsAdapter>()

    private val inquiryDetailsViewModel: InquiryDetailsViewModel by viewModels()

    var binding by autoCleared<InquiryDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var medicalRenewalPayment: MedicalRenewalPaymentUI
    lateinit var paymentRequestUI: PaymentRequestUI

    lateinit var mechanismTypeButton: RadioButton

    lateinit var params: InquiryDetailsFragmentArgs
    var sendDecryptionKey = false
    var title = ""
    var commission: Double = 0.0
    var newAmount: Double = 0.0
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
                paymentInquiry()
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        params = InquiryDetailsFragmentArgs.fromBundle(arguments!!)
        medicalRenewalPayment = params.medicalRenewalPaymentUI

        initializeViews()
    }

    fun initializeViews() {
        llContent.visibility = View.VISIBLE
        val adapter = MedicalRenewPaymentItemsAdapter(dataBindingComponent, appExecutors) { }
        this.adapter = adapter

        medicalRenewalPayment = params.medicalRenewalPaymentUI
        binding.title = params.title
        title = params.title
        binding.number = params.number

        calculateCommission(Constants.PaymentOption.OpayCredit)
        medicalRenewalPayment?.let {
            binding.medicalRenewalPayment = it
        }

        binding.rvDetails.adapter = adapter

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
            if (rb_fawry.isChecked) addRenewalRequest() else paymentInquiry()
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
                    inquiryDetailsViewModel.createFawryTransaction(paymentRequestUI.refId)// cowPayPayment(false)
            }

            state.medicalRenewalPayment?.let {
                medicalRenewalPayment = it
                if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card)
                    oPayPayment(Constants.PaymentOption.OpayCredit)
                else if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_channel)
                    oPayPayment(Constants.PaymentOption.OpayPOS)
                else if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_mobileWallet)
                    oPayPayment(Constants.PaymentOption.MobileWallet)
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
        val userInfo = UserInfo("", binding.number, sharedPref.mobile, medicalRenewalPayment.paymentItem?.engName)
        val payInput = PayInput(
            publickey = Constants.OPAY_PUBLIC_KEY,
            merchantId = Constants.OPAY_MERCHANT_ID,
            merchantName = Constants.OPAY_MERCHANT_NAME,
            reference = medicalRenewalPayment.paymentItem?.paymentRequestNumber!!,
            countryCode = "EG", // uppercase
            payAmount = (newAmount * 100).toLong(),
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
        intent.putExtra(CowpayConstantKeys.MerchantReferenceId, medicalRenewalPayment.paymentItem?.paymentRequestNumber)
        //order price780
        intent.putExtra(CowpayConstantKeys.Amount, newAmount.toString())
        //user data
        intent.putExtra(CowpayConstantKeys.Description, medicalRenewalPayment.paymentItem?.amount.toString())
        intent.putExtra(CowpayConstantKeys.CustomerName, params.number)
        intent.putExtra(CowpayConstantKeys.CustomerMobile, sharedPref.mobile)
        intent.putExtra(CowpayConstantKeys.CustomerEmail, "customer@customer.com")
        //user id
        intent.putExtra(CowpayConstantKeys.CustomerMerchantProfileId, medicalRenewalPayment.paymentItem?.paymentRequestNumber)


        startActivityForResult(intent, CowpayConstantKeys.PaymentMethodsActivityRequestCode)
    }

    //region
    private fun calculateCommission(paymentOption: Constants.PaymentOption) {
        when (paymentOption) {
            Constants.PaymentOption.OpayCredit -> commission = if (medicalRenewalPayment.paymentItem?.amount?.times(Constants.CC_COMMISSION)!! > Constants.MIN_COMMISSION) medicalRenewalPayment.paymentItem?.amount?.times(Constants.CC_COMMISSION) as Double else Constants.MIN_COMMISSION
            Constants.PaymentOption.OpayPOS -> commission = if (medicalRenewalPayment.paymentItem?.amount?.times(Constants.POS_COMMISSION)!! > Constants.MIN_COMMISSION) medicalRenewalPayment.paymentItem?.amount?.times(Constants.POS_COMMISSION) as Double else Constants.MIN_COMMISSION
            Constants.PaymentOption.Fawry -> commission = if (medicalRenewalPayment.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION)!! > Constants.MIN_COMMISSION) medicalRenewalPayment.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION) as Double else Constants.MIN_COMMISSION
        }
        commission = Math.round(commission * 10.0) / 10.0
        newAmount = (medicalRenewalPayment.paymentItem?.amount ?: 0.0) + commission
        binding.newAmount = newAmount
        updateCommissionInList()
    }

    private fun updateCommissionInList() {
        medicalRenewalPayment.paymentItem?.paymentDetailsItems?.let {
            it.let {
                val tmp = it.toMutableList()
                tmp.add(MedicalRenewalPaymentUI.PaymentDetailsItem(getString(R.string.commission), commission.toString()))
                adapter.submitList(tmp)
                rvDetails.adapter = adapter
            }
        }
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

    fun paymentInquiry(){
        inquiryDetailsViewModel.paymentInquiry(sharedPref.mobile, params.number, params.serviceID, medicalRenewalPayment.requestID, medicalRenewalPayment.paymentItem?.amount.toString(), if (rb_syndicate.isChecked) Constants.DELIVERY_LOCATION_SYNDICATE else if (rb_home.isChecked) Constants.DELIVERY_LOCATION_HOME else Constants.DELIVERY_LOCATION_MAIN_SYNDICATE, edAddress.text.toString(), edMobile.text.toString())
    }

    fun addRenewalRequest(){
        inquiryDetailsViewModel.addRenewalRequest(sharedPref.mobile, params.number, medicalRenewalPayment.paymentItem?.engName ?: "", params.serviceID.toInt(),
            if (rb_card.isChecked) "card" else "pos",
            if (rb_card.isChecked) 3 else if (rb_channel.isChecked) 2 else 4,
            if (rb_syndicate.isChecked) Constants.DELIVERY_LOCATION_SYNDICATE else if (rb_home.isChecked) Constants.DELIVERY_LOCATION_HOME else Constants.DELIVERY_LOCATION_MAIN_SYNDICATE,
            edAddress.text.toString(), edMobile.text.toString()
        )
    }
    //endregion

    fun navController() = findNavController()
}
