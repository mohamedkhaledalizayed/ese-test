package com.neqabty.presentation.ui.inquiryDetails

import android.app.Activity
import android.content.Intent
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
import com.efinance.mobilepaymentsdk.*
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.InquiryDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.inquiry_details_fragment.*
import me.cowpay.PaymentMethodsActivity
import me.cowpay.util.CowpayConstantKeys
import javax.inject.Inject

class InquiryDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var adapter by autoCleared<PaymentItemsAdapter>()

    lateinit var inquiryDetailsViewModel: InquiryDetailsViewModel

    var binding by autoCleared<InquiryDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var memberItem: MemberUI

    lateinit var paymentGateway: PaymentGateway
    lateinit var paymentCreationRequest: PaymentCreationRequest
    lateinit var mechanismTypeButton: RadioButton

    lateinit var params: InquiryDetailsFragmentArgs
    var sendDecryptionKey = false

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
                inquiryDetailsViewModel.paymentInquiry(memberItem.engineerNumber, params.serviceID, memberItem.requestID, memberItem.amount.toString())
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        params = InquiryDetailsFragmentArgs.fromBundle(arguments!!)
        memberItem = params.memberItem

//        initializeViews()
        inquiryDetailsViewModel.paymentInquiry(memberItem.engineerNumber, params.serviceID, memberItem.requestID, memberItem.amount.toString())
    }

    fun initializeViews() {
        llContent.visibility = View.VISIBLE
        val adapter = PaymentItemsAdapter(dataBindingComponent, appExecutors) { }
        this.adapter = adapter

        binding.title = params.title
        memberItem?.let {
            binding.memberItem = it

            it.payments?.let {
                adapter.submitList(it)
            }
        }

        binding.rvDetails.adapter = adapter

        rb_card.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                ivCard.visibility = View.VISIBLE
                ivChannels.visibility = View.GONE
            }
        }
        rb_channel.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                ivChannels.visibility = View.VISIBLE
                ivCard.visibility = View.GONE
            }
        }

        bPay.setOnClickListener {
            llSuperProgressbar.visibility = View.VISIBLE
//            createPayment()
            cowPayPayment()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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
                    showAlert(getString(R.string.payment_successful) + PaymentGatewayReferenceId) {
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
            if (sendDecryptionKey) {
                llSuperProgressbar.visibility = View.INVISIBLE
                val paymentMethod = mechanismTypeButton.getText().toString()
                if (paymentMethod == getString(R.string.payment_card)) {
                    navController().navigate(
                            InquiryDetailsFragmentDirections.openPayment(memberItem, paymentCreationResponse.OriginalSenderRequestNumber,
                                    paymentCreationResponse.CardRequestNumber, paymentCreationResponse.SessionId, paymentCreationResponse.TotalAuthorizationAmount.toString())
                    )
                } else if (paymentMethod == getString(R.string.payment_channel) ||
                        paymentMethod == getString(R.string.payment_wallet) ||
                        paymentMethod == getString(R.string.payment_meeza)) {
                    showAlert(getString(R.string.payment_reference) + "  " + paymentCreationResponse.CardRequestNumber) {
                        navController().popBackStack()
                        navController().navigate(R.id.homeFragment)
                    }
//                    "senderRequestNumber" + response.OriginalSenderRequestNumber
                }
            } else {
                state.member?.let {
                    memberItem.paymentCreationRequest = it.paymentCreationRequest
//                    // static set
//                    memberItem.paymentCreationRequest?.sender?.id = "071"
//                    memberItem.paymentCreationRequest?.serviceCode = "171"
//                    memberItem.paymentCreationRequest?.settlementAmounts?.settlementAccountCode = "647"
//                    memberItem.paymentCreationRequest?.senderRequestNumber = it.paymentCreationRequest?.senderRequestNumber!!
//                    var intent = Intent(context, PaymentMethodsActivity::class.java)
//                    intent.putExtra(CowpayConstantKeys.MerchantCode, "GHIu9nk25D5z")
//                    intent.putExtra(
//                            CowpayConstantKeys.MerchantHashKey,
//                            "MerchantHashKey"
//                    )
                    initializeViews()
                }
            }
        }
    }

    //region

    fun cowPayPayment() {
        var intent = Intent(context, PaymentMethodsActivity::class.java)

        var PaymentMethod = ArrayList<String>()
        PaymentMethod.add(CowpayConstantKeys.CreditCardMethod)
//            PaymentMethod.add(CowpayConstantKeys.FawryMethod)
        intent.putExtra(CowpayConstantKeys.PaymentMethod, PaymentMethod)

        intent.putExtra(CowpayConstantKeys.AuthorizationToken, "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImQ1OGUwYzQxMGNhYzFiYTYzNmZlZmY5ODMwNDhkNGFjNDg3MWQ3M2Q3OTI2ZGQ4ZDFmYWYwM2IzYWY1YTYyM2Y0OWY1NWRmOWQ1MGNhYTFjIn0.eyJhdWQiOiIxIiwianRpIjoiZDU4ZTBjNDEwY2FjMWJhNjM2ZmVmZjk4MzA0OGQ0YWM0ODcxZDczZDc5MjZkZDhkMWZhZjAzYjNhZjVhNjIzZjQ5ZjU1ZGY5ZDUwY2FhMWMiLCJpYXQiOjE2MDY5MzA2MzUsIm5iZiI6MTYwNjkzMDYzNSwiZXhwIjoxNjM4NDY2NjM1LCJzdWIiOiIzODciLCJzY29wZXMiOltdfQ.PTyQdVixvfIJRcsMWdId7TrwOuGGPaNIor24XsHLExNMPcOemh1nmw6sRWgem9kb4xKB73Q3DWcRnudv44vhDw")

        //set environment production or sandBox
        //CowpayConstantKeys.Production or CowpayConstantKeys.SandBox
        intent.putExtra(CowpayConstantKeys.PaymentEnvironment, CowpayConstantKeys.SandBox)
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
        intent.putExtra(CowpayConstantKeys.MerchantReferenceId, memberItem.paymentCreationRequest?.senderRequestNumber)
        //order price780
        intent.putExtra(CowpayConstantKeys.Amount, memberItem.paymentCreationRequest?.settlementAmounts?.amount?.toString())
        //user data
        intent.putExtra(CowpayConstantKeys.CustomerName, memberItem.engineerName)
        intent.putExtra(CowpayConstantKeys.CustomerMobile, PreferencesHelper(requireContext()).mobile)
        intent.putExtra(CowpayConstantKeys.CustomerEmail, "customer@customer.com")
        //user id
        intent.putExtra(CowpayConstantKeys.CustomerMerchantProfileId, memberItem.paymentCreationRequest?.userUniqueIdentifier)


        startActivityForResult(intent, CowpayConstantKeys.PaymentMethodsActivityRequestCode)
    }

    fun createPayment() {
        try {

            paymentGateway = PaymentGateway(activity, memberItem.paymentCreationRequest?.sender?.password)
            paymentCreationRequest = PaymentCreationRequest()

//            paymentCreationRequest.Sender.Id = memberItem.paymentCreationRequest?.sender?.id
            paymentCreationRequest.Sender.Id = "071"
            paymentCreationRequest.Sender.Name = memberItem.paymentCreationRequest?.sender?.name
//            paymentCreationRequest.Sender.Name = "MSAD"
            paymentCreationRequest.Sender.Password = memberItem.paymentCreationRequest?.sender?.password
            paymentCreationRequest.Description = "Test"
//            paymentCreationRequest.SenderInvoiceNumber = memberItem.paymentCreationRequest?.senderRequestNumber
            paymentCreationRequest.SenderInvoiceNumber = memberItem.paymentCreationRequest?.senderRequestNumber
            paymentCreationRequest.AdditionalInfo = "Test"

//            paymentCreationRequest.SenderRequestNumber = memberItem.paymentCreationRequest?.senderRequestNumber + "032110010100"
            paymentCreationRequest.SenderRequestNumber = memberItem.paymentCreationRequest?.senderRequestNumber
//            paymentCreationRequest.ServiceCode = memberItem.paymentCreationRequest?.serviceCode
            paymentCreationRequest.ServiceCode = "171"

            val settlementAmount = PaymentCreationRequest.SettlementAmount()

            settlementAmount.Amount = memberItem.paymentCreationRequest?.settlementAmounts?.amount?.toDouble()!!
//            settlementAmount.SettlementAccountCode = memberItem.paymentCreationRequest?.settlementAmounts?.settlementAccountCode!!.toInt()
            settlementAmount.SettlementAccountCode = 647
            settlementAmount.Description = "Test"

            paymentCreationRequest.SettlementAmounts.add(settlementAmount)

            paymentCreationRequest.Currency = memberItem.paymentCreationRequest?.currency
//            paymentCreationRequest.Currency = "818"

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
            paymentCreationRequest.RequestExpiryDate = "2020-12-07"

            paymentCreationRequest.UserUniqueIdentifier = memberItem.paymentCreationRequest?.userUniqueIdentifier
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
                    inquiryDetailsViewModel.paymentInquiry(memberItem.engineerNumber, params.serviceID, memberItem.requestID, memberItem.amount.toString())
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
    //endregion

    fun navController() = findNavController()
}
