package com.neqabty.presentation.ui.syndicateServicesDetails

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
import com.neqabty.databinding.SyndicateServicesDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.SyndicateServicesPaymentRequestUI
import com.neqabty.presentation.entities.SyndicateServicesPaymentUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.syndicate_services_details_fragment.*
import me.cowpay.PaymentMethodsActivity
import me.cowpay.util.CowpayConstantKeys
import team.opay.business.cashier.sdk.api.*
import team.opay.business.cashier.sdk.pay.PaymentTask
import javax.inject.Inject

@AndroidEntryPoint
class SyndicateServicesDetailsFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var adapter by autoCleared<SyndicateServicesPaymentItemsAdapter>()

    private val syndicateServicesDetailsViewModel: SyndicateServicesDetailsViewModel by viewModels()

    var binding by autoCleared<SyndicateServicesDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var syndicateServicesPaymentUI: SyndicateServicesPaymentUI
    lateinit var syndicateServicesPaymentRequestUI: SyndicateServicesPaymentRequestUI

    lateinit var mechanismTypeButton: RadioButton

    lateinit var params: SyndicateServicesDetailsFragmentArgs
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
            R.layout.syndicate_services_details_fragment,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        syndicateServicesDetailsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        syndicateServicesDetailsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                addSyndicateServicesPaymentRequest()
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        params = SyndicateServicesDetailsFragmentArgs.fromBundle(arguments!!)

        initializeViews()
    }

    fun initializeViews() {
        llContent.visibility = View.VISIBLE
        val adapter = SyndicateServicesPaymentItemsAdapter(dataBindingComponent, appExecutors) { }
        this.adapter = adapter

        syndicateServicesPaymentUI = params.syndicateServicesPaymentUI
        binding.title = params.title
        binding.name = sharedPref.name
        binding.number = sharedPref.user
//        binding.newAmount = sharedPref.user

        calculateCommission(Constants.PaymentOption.OpayCredit)
        syndicateServicesPaymentUI?.let {
            binding.syndicateServicesPaymentUI = it
        }

        syndicateServicesPaymentUI.paymentItem?.paymentDetailsItems?.let {
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

        tvChannels.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://cashier.opaycheckout.com/map")
                )
            )
        }

        bPay.setOnClickListener {
            if ((rb_home.isChecked && edAddress.text.toString().isBlank())) {
                showAlert(getString(R.string.invalid_data))
                return@setOnClickListener
            } else if (!isDataValid(edMobile.text.toString()))
                return@setOnClickListener
            llSuperProgressbar.visibility = View.VISIBLE
//            createPayment()
            addSyndicateServicesPaymentRequest()
        }
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
                    navController().popBackStack()
                    navController().navigate(R.id.homeFragment)
                }
            } else if (responseCode == CowpayConstantKeys.SuccessCode) {
                var responseMSG = data.extras!!.getString(CowpayConstantKeys.ResponseMessage)
                var PaymentGatewayReferenceId =
                    data.extras!!.getString(CowpayConstantKeys.PaymentGatewayReferenceId)
                responseMSG?.let {
                    showAlert(
                        if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card) getString(
                            R.string.payment_successful
                        ) + PaymentGatewayReferenceId else getString(R.string.payment_reference) + PaymentGatewayReferenceId
                    ) {
                        navController().popBackStack()
                        navController().navigate(R.id.homeFragment)
                    }
                }
            }
        }
    }

    private fun handleViewState(state: SyndicateServicesDetailsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading) {
            state.syndicateServicesPaymentRequestUI?.let {
                syndicateServicesPaymentRequestUI = it
                if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card)
                    oPayPayment(Constants.PaymentOption.OpayCredit)
                else if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_channel)
                    oPayPayment(Constants.PaymentOption.OpayPOS)
                else if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_mobileWallet)
                    oPayPayment(Constants.PaymentOption.MobileWallet)
                else
                    cowPayPayment(false)
            }
        }
    }

    //region

    fun oPayPayment(paymentOption: Constants.PaymentOption) {
        val paymentType = when (paymentOption) {
            Constants.PaymentOption.OpayCredit -> "BankCard"
            Constants.PaymentOption.OpayPOS -> "ReferenceCode"
            Constants.PaymentOption.MobileWallet -> "MWALLET"
            else -> ""
        }
        PaymentTask.sandBox = Constants.OPAY_MODE
        val userInfo = UserInfo(
            syndicateServicesPaymentRequestUI.amount.toString(),
            binding.number,
            sharedPref.mobile,
            syndicateServicesPaymentUI.paymentItem?.engName
        )
        val payInput = PayInput(
            publickey = Constants.OPAY_PUBLIC_KEY,
            merchantId = Constants.OPAY_MERCHANT_ID,
            merchantName = Constants.OPAY_MERCHANT_NAME,
            reference = syndicateServicesPaymentRequestUI.refId,
            countryCode = "EG", // uppercase
            payAmount = (syndicateServicesPaymentRequestUI.amount?.times(100))?.toLong()!!,
            currency = "EGP", // uppercase
            productName = "annualSubscription",
            productDescription = "android",
            callbackUrl = Constants.OPAY_PAYMENT_CALLBACK_URL,
            paymentType = paymentType, // optional
            expireAt = if (paymentOption == Constants.PaymentOption.OpayCredit) 30 else 2880,
            userClientIP = "110.246.160.183",
            userInfo = userInfo
        )

        llSuperProgressbar.visibility = View.VISIBLE
        PaymentTask(this).createOrder(payInput, callback = { status, response ->
            llSuperProgressbar.visibility = View.GONE
            when (status) {
                Status.ERROR -> { // error
                    Toast.makeText(
                        requireContext(), response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
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
        intent.putExtra(CowpayConstantKeys.MerchantReferenceId, syndicateServicesPaymentRequestUI.refId)
        //order price780
        intent.putExtra(CowpayConstantKeys.Amount, syndicateServicesPaymentRequestUI.amount.toString())
        //user data
        intent.putExtra(CowpayConstantKeys.Description, syndicateServicesPaymentRequestUI.amount.toString())
        intent.putExtra(CowpayConstantKeys.CustomerName, sharedPref.user)
        intent.putExtra(CowpayConstantKeys.CustomerMobile, sharedPref.mobile)
        intent.putExtra(CowpayConstantKeys.CustomerEmail, "customer@customer.com")
        //user id
        intent.putExtra(CowpayConstantKeys.CustomerMerchantProfileId, syndicateServicesPaymentRequestUI.refId)


        startActivityForResult(intent, CowpayConstantKeys.PaymentMethodsActivityRequestCode)
    }

    //region
    private fun calculateCommission(paymentOption: Constants.PaymentOption) {
        when (paymentOption) {
            Constants.PaymentOption.OpayCredit -> newAmount =
                syndicateServicesPaymentUI.amounts?.get(1)?.cardAmount!!
            Constants.PaymentOption.OpayPOS -> newAmount =
                syndicateServicesPaymentUI.amounts?.get(1)?.posAmount!!
            Constants.PaymentOption.Fawry -> newAmount = syndicateServicesPaymentUI.amounts?.get(0)?.posAmount!!
            else -> {}
        }
        binding.newAmount = newAmount
//        when (paymentOption) {
//            Constants.PaymentOption.OpayCredit -> commission = if (syndicateServicesPaymentUI.paymentItem?.amount?.times(Constants.CC_COMMISSION)!! > Constants.MIN_COMMISSION) syndicateServicesPaymentUI.paymentItem?.amount?.times(Constants.CC_COMMISSION) as Double else Constants.MIN_COMMISSION
//            Constants.PaymentOption.OpayPOS -> commission = if (syndicateServicesPaymentUI.paymentItem?.amount?.times(Constants.POS_COMMISSION)!! > Constants.MIN_COMMISSION) syndicateServicesPaymentUI.paymentItem?.amount?.times(Constants.POS_COMMISSION) as Double else Constants.MIN_COMMISSION
//            Constants.PaymentOption.Fawry -> commission = if (syndicateServicesPaymentUI.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION)!! > Constants.MIN_COMMISSION) syndicateServicesPaymentUI.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION) as Double else Constants.MIN_COMMISSION
//        }
//        commission = Math.round(commission * 10.0) / 10.0
//        newAmount = (syndicateServicesPaymentUI.paymentItem?.amount ?: 0) + commission
//        binding.newAmount = newAmount
//        updateCommissionInList()
    }

//    private fun updateCommissionInList() {
//        syndicateServicesPaymentUI.paymentItem?.paymentDetailsItems?.let {
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
        return if (mobile.matches(Regex("[0-9]*")) && mobile.trim().length == 11 && (mobile.substring(
                0,
                3
            ).equals("012") || mobile.substring(0, 3).equals("010") || mobile.substring(0, 3)
                .equals("011") || mobile.substring(0, 3).equals("015"))
        )
            true
        else {
            showAlert(getString(R.string.invalid_mobile))
            false
        }
    }

    fun addSyndicateServicesPaymentRequest() {
        syndicateServicesDetailsViewModel.addSyndicateServicesPaymentRequest(
            sharedPref.mobile,
            sharedPref.user,
            sharedPref.name,
            params.serviceID,
            -1,
            if (rb_card.isChecked) "card" else "pos",
            if (rb_card.isChecked) 2 else if (rb_channel.isChecked) 2 else 1,
            if (rb_syndicate.isChecked) Constants.DELIVERY_LOCATION_SYNDICATE else if (rb_home.isChecked) Constants.DELIVERY_LOCATION_HOME else Constants.DELIVERY_LOCATION_MAIN_SYNDICATE,
            edAddress.text.toString(),
            edMobile.text.toString()
        )
    }

    //endregion

    fun navController() = findNavController()
}