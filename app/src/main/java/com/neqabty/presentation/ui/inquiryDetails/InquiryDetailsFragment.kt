package com.neqabty.presentation.ui.inquiryDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.cowpay.cowpaysdk.models.Card
import com.cowpay.cowpaysdk.models.CashCollection
import com.cowpay.cowpaysdk.models.Fawry
import com.cowpay.cowpaysdk.sdk.CowpayCallback
import com.cowpay.cowpaysdk.sdk.CowpaySDK
import com.cowpay.cowpaysdk.sdk.CowpaySDKPaymentMethod
import com.cowpay.cowpaysdk.sdk.model.PaymentInfo
import com.efinance.mobilepaymentsdk.*
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.InquiryDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.ui.medicalRenewDetails.MedicalRenewPaymentItemsAdapter
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.inquiry_details_fragment.*
import java.util.*
import javax.inject.Inject

class InquiryDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var adapter by autoCleared<MedicalRenewPaymentItemsAdapter>()

    lateinit var inquiryDetailsViewModel: InquiryDetailsViewModel

    var binding by autoCleared<InquiryDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var medicalRenewalPayment: MedicalRenewalPaymentUI

    lateinit var paymentGateway: PaymentGateway
    lateinit var paymentCreationRequest: PaymentCreationRequest
    lateinit var mechanismTypeButton: RadioButton

    lateinit var params: InquiryDetailsFragmentArgs
    var sendDecryptionKey = false
    var title = ""
    var commission: Double = 0.0
    var newAmount: Double = 0.0
    lateinit var paymentCreationResponse: PaymentCreationResponse
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
        inquiryDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(InquiryDetailsViewModel::class.java)

        inquiryDetailsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        inquiryDetailsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                inquiryDetailsViewModel.paymentInquiry(PreferencesHelper(requireContext()).mobile, params.number, params.serviceID, medicalRenewalPayment.requestID, medicalRenewalPayment.paymentItem?.amount.toString())
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

        calculateCommission(true)
        medicalRenewalPayment?.let {
            binding.medicalRenewalPayment = it
        }

        binding.rvDetails.adapter = adapter

        rb_card.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                calculateCommission(true)
                ivCard.visibility = View.VISIBLE
                ivChannels.visibility = View.GONE
            }
        }
        rb_channel.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                calculateCommission(false)
                ivChannels.visibility = View.VISIBLE
                ivCard.visibility = View.GONE
            }
        }

        bPay.setOnClickListener {
            llSuperProgressbar.visibility = View.VISIBLE
//            createPayment()
            inquiryDetailsViewModel.paymentInquiry(PreferencesHelper(requireContext()).mobile, params.number, params.serviceID, medicalRenewalPayment.requestID, medicalRenewalPayment.paymentItem?.amount.toString())
        }
    }

    private fun handleViewState(state: InquiryDetailsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading) {
            state.medicalRenewalPayment?.let {
                medicalRenewalPayment = it
                if (rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card)
                    cowPayPayment(true)
                else
                    cowPayPayment(false)
            }
        }
//            if (sendDecryptionKey) {
//                llSuperProgressbar.visibility = View.INVISIBLE
//                val paymentMethod = mechanismTypeButton.getText().toString()
//                if (paymentMethod == getString(R.string.payment_card)) {
//                    navController().navigate(
//                            InquiryDetailsFragmentDirections.openPayment(medicalRenewalPayment, paymentCreationResponse.OriginalSenderRequestNumber,
//                                    paymentCreationResponse.CardRequestNumber, paymentCreationResponse.SessionId, paymentCreationResponse.TotalAuthorizationAmount.toString())
//                    )
//                } else if (paymentMethod == getString(R.string.payment_channel) ||
//                        paymentMethod == getString(R.string.payment_wallet) ||
//                        paymentMethod == getString(R.string.payment_meeza)) {
//                    showAlert(getString(R.string.payment_reference) + "  " + paymentCreationResponse.CardRequestNumber) {
//                        navController().popBackStack()
//                        navController().navigate(R.id.homeFragment)
//                    }
////                    "senderRequestNumber" + response.OriginalSenderRequestNumber
//                }
//            } else {
//                state.medicalRenewalPayment?.let {
////                    medicalRenewalPayment.paymentCreationRequest = it.paymentCreationRequest
////                    // static set
////                    memberItem.paymentCreationRequest?.sender?.id = "071"
////                    memberItem.paymentCreationRequest?.serviceCode = "171"
////                    memberItem.paymentCreationRequest?.settlementAmounts?.settlementAccountCode = "647"
////                    memberItem.paymentCreationRequest?.senderRequestNumber = it.paymentCreationRequest?.senderRequestNumber!!
////                    var intent = Intent(context, PaymentMethodsActivity::class.java)
////                    intent.putExtra(CowpayConstantKeys.MerchantCode, "GHIu9nk25D5z")
////                    intent.putExtra(
////                            CowpayConstantKeys.MerchantHashKey,
////                            "MerchantHashKey"
////                    )
//                    initializeViews()
//                }
//            }
//        }
    }

    //region

    fun cowPayPayment(isCredit: Boolean) {
        CowpaySDK.init(Constants.cowpayAuthToken, Constants.COWPAY_MERCHANT_CODE, Constants.COWPAY_MERCHANT_KEY , Constants.COWPAY_MODE)

        val payment = PaymentInfo(
                merchantReferenceId = medicalRenewalPayment.paymentItem?.paymentRequestNumber!!,
                customerMerchantProfileId = medicalRenewalPayment.paymentItem?.paymentRequestNumber!!,
                amount = newAmount,
                description = medicalRenewalPayment.paymentItem?.amount.toString(),
                customerName = params.number,
                customerEmail = "customer@customer.com",
                customerMobile = PreferencesHelper(requireContext()).mobile)

        var paymentMethods = emptyArray<CowpaySDKPaymentMethod>()
        if (isCredit)
            paymentMethods = arrayOf(CowpaySDKPaymentMethod.CARD)
        else
            paymentMethods = arrayOf(CowpaySDKPaymentMethod.FAWRY)

        CowpaySDK.setPaymentMethodAvailability(paymentMethods)

        CowpaySDK.launch(requireActivity() ,payment, object : CowpayCallback {
            override fun successByFawry(fawry: Fawry) {
                showAlert(getString(R.string.payment_reference) + fawry.paymentGatewayReferenceId) {
                    navController().popBackStack()
                    navController().navigate(R.id.homeFragment)
                }
            }

            override fun successByCreditCard(card: Card) {
                showAlert(getString(R.string.payment_successful) + card.paymentGatewayReferenceId) {
                    navController().popBackStack()
                    navController().navigate(R.id.homeFragment)
                }
            }

            override fun successByCashCollection(cashCollection: CashCollection) {
                // Successful payment using Cash Collection
            }

            override fun error(it: String) {
                showAlert(getString(R.string.payment_canceled)) {
                    navController().popBackStack()
                    navController().navigate(R.id.homeFragment)
                }
            }

            override fun closeByUser() {
                showAlert(getString(R.string.payment_canceled)) {
                    navController().popBackStack()
                    navController().navigate(R.id.homeFragment)
                }
            }
        })
    }

    fun createPayment() {
        try {

            paymentGateway = PaymentGateway(activity, "1234")
            paymentCreationRequest = PaymentCreationRequest()

//            paymentCreationRequest.Sender.Id = memberItem.paymentCreationRequest?.sender?.id
            paymentCreationRequest.Sender.Id = "071"
//            paymentCreationRequest.Sender.Name = memberItem.paymentCreationRequest?.sender?.name
            paymentCreationRequest.Sender.Name = "MSAD"
            paymentCreationRequest.Sender.Password = "1234"
            paymentCreationRequest.Description = "Test"
//            paymentCreationRequest.SenderInvoiceNumber = memberItem.paymentCreationRequest?.senderRequestNumber
            paymentCreationRequest.SenderInvoiceNumber = medicalRenewalPayment.requestID
            paymentCreationRequest.AdditionalInfo = "Test"

//            paymentCreationRequest.SenderRequestNumber = memberItem.paymentCreationRequest?.senderRequestNumber + "032110010100"
            paymentCreationRequest.SenderRequestNumber = medicalRenewalPayment.requestID
//            paymentCreationRequest.ServiceCode = memberItem.paymentCreationRequest?.serviceCode
            paymentCreationRequest.ServiceCode = "171"

            val settlementAmount = PaymentCreationRequest.SettlementAmount()

            settlementAmount.Amount = medicalRenewalPayment.paymentItem?.amount?.toDouble()!!
//            settlementAmount.SettlementAccountCode = memberItem.paymentCreationRequest?.settlementAmounts?.settlementAccountCode!!.toInt()
            settlementAmount.SettlementAccountCode = 647
            settlementAmount.Description = "Test"

            paymentCreationRequest.SettlementAmounts.add(settlementAmount)

//            paymentCreationRequest.Currency = memberItem.paymentCreationRequest?.currency
            paymentCreationRequest.Currency = "818"

            mechanismTypeButton = binding.root.findViewById(rgPaymentMechanismType.getCheckedRadioButtonId())

            if (mechanismTypeButton.getText().toString() == getString(R.string.payment_card)) {
                paymentCreationRequest.PaymentMechanism.Type = PaymentCreationRequest.PaymentMechanismType.Card
            } else if (mechanismTypeButton.getText().toString() == getString(R.string.payment_channel)) {
                paymentCreationRequest.PaymentMechanism.Type = PaymentCreationRequest.PaymentMechanismType.Channel

                paymentCreationRequest.PaymentMechanism.Channel.Email = "xxxx@xx.xx"
                paymentCreationRequest.PaymentMechanism.Channel.MobileNumber = PreferencesHelper(requireContext()).mobile
            } else if (mechanismTypeButton.getText().toString() == getString(R.string.payment_wallet)) {
                paymentCreationRequest.PaymentMechanism.Type = PaymentCreationRequest.PaymentMechanismType.MobileWallet
                paymentCreationRequest.PaymentMechanism.MobileWallet.MobileNumber = PreferencesHelper(requireContext()).mobile
            } else if (mechanismTypeButton.getText().toString() == getString(R.string.payment_meeza)) {
                paymentCreationRequest.PaymentMechanism.Type = PaymentCreationRequest.PaymentMechanismType.Meeza
                paymentCreationRequest.PaymentMechanism.Meeza.Tahweel.MobileNumber = PreferencesHelper(requireContext()).mobile
            }

//            paymentCreationRequest.RequestExpiryDate = memberItem.paymentCreationRequest?.requestExpiryDate
            paymentCreationRequest.RequestExpiryDate = "2020-12-09"

            paymentCreationRequest.UserUniqueIdentifier = medicalRenewalPayment.requestID
//            paymentCreationRequest.UserUniqueIdentifier = "12346743298546"

//            val publicKey = memberItem.paymentCreationRequest?.publicKey
            val publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0MAVRsQax/cxZwZcfp+s0mzZtFGNRpuRLnyKUosdnlHn2JC1FSHpnmsON1Mp/SY+GR/Wk1kO2QGNNdszikPgAnN5/fcX6JPIkzSHCMLqPxUNqaCfn0eaLrwgsc1SCwlm+f8c+CseG3OeR+sUdq52PHf7edpjC60V4bNo/gEVRLV+VsvBde8jhet6Z/wRrKL5K1MQH0ByYn9upf96myRiTOSvocuBVHnlb2O+tapLVrNq7dMNXCHHB7IuNCFvP0f0QILeDW5CxebcsTItzxLLurKtA1lTWv+Ao9oqexy1BJzMytpS+BAz9kNzmt/g7RcdbXd0MxFotvoHjl2jwE1wLwIDAQAB"

//            inquiryDetailsViewModel.encryptData(paymentCreationRequest.Sender.Name, paymentCreationRequest.Sender.Password, paymentCreationRequest.serialize())
            paymentCreationRequest.serialize()

            val successCallback: ((response: PaymentCreationResponse) -> Unit) = { response ->
                sendDecryptionKey = true
                paymentCreationResponse = response

//                if (mechanismTypeButton.getText().toString() == getString(R.string.payment_channel)) {
//
//                    offlineSetPaid(response.OriginalSenderRequestNumber)
//                } else {
//                    llSuperProgressbar.visibility = View.INVISIBLE
//                    val paymentMethod = mechanismTypeButton.getText().toString()
//                    if (paymentMethod == getString(R.string.payment_card)) {
//                        navController().navigate(
//                                MedicalRenewDetailsFragmentDirections.openPayment(MemberUI(), paymentCreationResponse.OriginalSenderRequestNumber,
//                                        paymentCreationResponse.CardRequestNumber, paymentCreationResponse.SessionId, paymentCreationResponse.TotalAuthorizationAmount.toString())
//                        )
//                    } else if (paymentMethod == getString(R.string.payment_channel) ||
//                            paymentMethod == getString(R.string.payment_wallet) ||
//                            paymentMethod == getString(R.string.payment_meeza)) {
//                        showAlert(getString(R.string.payment_reference) + "  " + paymentCreationResponse.CardRequestNumber) {
//                            navController().popBackStack()
//                            navController().navigate(R.id.homeFragment)
//                        }
////                    "senderRequestNumber" + response.OriginalSenderRequestNumber
//                    }
//                }
                inquiryDetailsViewModel.sendDecryptionKey(response.OriginalSenderRequestNumber, response.RequestDecryptionKey)
            }

            val failureCallback = {
                llSuperProgressbar.visibility = View.INVISIBLE
                showConnectionAlert(requireContext(), retryCallback = {
                    llContent.visibility = View.INVISIBLE
                    inquiryDetailsViewModel.paymentInquiry(PreferencesHelper(requireContext()).mobile, params.number, params.serviceID, medicalRenewalPayment.requestID, medicalRenewalPayment.paymentItem?.amount.toString())
                }, cancelCallback = {
                    navController().navigateUp()
                })
            }

            paymentGateway.CreatePayment(paymentCreationRequest, "", publicKey, MobilePaymentCreationCallback(successCallback, failureCallback))
        } catch (ex: Exception) {
//            Log.i("Error", ex.message)
        }
    }

//    private fun completePaymentCreation(encryptedData: String) {
//        try {
//            val publicKey = memberItem.paymentCreationRequest?.publicKey
// //            var signature = paymentCreationRequest.serialize()
// //            Log.d("NEQBTY", signature)
//
// //            val signature = "qf9Qo2fZ792GyoDiXik6eAQ6fadqHaib+yaBTBp4PGj1xf6KlVn3pP822D8VyADm+OEvuPVtLc3nvutiTEDZgo4oeJnUIwXveAzQUV6RM9oyMfOu78Kj9NcD3wMW1Jb6hwAKfHQ/tLsY7oJYIhXmh1x2INU9am6K6JrD468ToBNhWU6Df9SlJsMezZWMLrG0Z4bElqTsIVcpATiu8rJ4lGHMl+qC4AX+2pAViz31TPXGREdYsQpLDHbnBXaVvLLY8fKNyOjDaskxEoeJikPdzkAmBn1HDmzwWhb9GyYRsGJw4wm1szIXrMIRvgGYIarsVwxL7uaSWs/yrjHUFhT8AQ=="
//
//            paymentGateway.CreatePayment(paymentCreationRequest, encryptedData, publicKey, MobilePaymentCreationCallback(requireContext(), mechanismTypeButton.getText().toString(), navController()))
//        } catch (ex: Exception) {
////            Log.i("Error", ex.message)
//        }
//    }
    // endregion

    // region callback
    class MobilePaymentCreationCallback(
            private val successCallback: ((response: PaymentCreationResponse) -> Unit),
            private val failureCallback: (() -> Unit)
    ) : PaymentCreationCallback {
        override fun onSuccess(response: PaymentCreationResponse) {
            successCallback.invoke(response)
        }

        override fun onError(paymentException: PaymentException) {
//            Log.e("NEQABTY", paymentException.details.message)
            failureCallback.invoke()
        }
    }

//    private fun generateRequestNumber(): String {
//        val random = Random()
//        val requestNumber = random.nextInt(999999999)
//
//        return requestNumber.toString()
//    }

    fun offlineSetPaid(originalSenderRequestNumber: String) {

//        inquiryDetailsViewModel.setPaid(originalSenderRequestNumber)
    }

    private fun calculateCommission(isCredit: Boolean) {
        if (isCredit) {
            commission = if (medicalRenewalPayment.paymentItem?.amount?.times(Constants.CC_COMMISSION)!! > Constants.MIN_COMMISSION) medicalRenewalPayment.paymentItem?.amount?.times(Constants.CC_COMMISSION) as Double else Constants.MIN_COMMISSION
        } else {
            commission = if (medicalRenewalPayment.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION)!! > Constants.MIN_COMMISSION) medicalRenewalPayment.paymentItem?.amount?.times(Constants.FAWRY_COMMISSION) as Double else Constants.MIN_COMMISSION
        }
        commission = Math.round(commission * 10.0) / 10.0
        newAmount = (medicalRenewalPayment.paymentItem?.amount ?: 0) + commission
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
    //endregion

    fun navController() = findNavController()
}
