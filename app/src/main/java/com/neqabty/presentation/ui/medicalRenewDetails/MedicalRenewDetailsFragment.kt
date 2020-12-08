package com.neqabty.presentation.ui.medicalRenewDetails

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
import com.neqabty.databinding.MedicalRenewDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.medical_renew_details_fragment.*
import me.cowpay.PaymentMethodsActivity
import me.cowpay.util.CowpayConstantKeys
import javax.inject.Inject

class MedicalRenewDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var adapter by autoCleared<MedicalRenewPaymentItemsAdapter>()

    lateinit var medicalRenewDetailsViewModel: MedicalRenewDetailsViewModel

    var binding by autoCleared<MedicalRenewDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var medicalRenewalPaymentUI: MedicalRenewalPaymentUI

    lateinit var paymentGateway: PaymentGateway
    lateinit var paymentCreationRequest: PaymentCreationRequest
    lateinit var mechanismTypeButton: RadioButton

    var sendDecryptionKey = false
    var deliveryType: Int = Constants.DELIVERY_LOCATION_HOME
    lateinit var address: String
    lateinit var mobile: String
    lateinit var medicalRenewalUI: MedicalRenewalUI

    lateinit var paymentCreationResponse: PaymentCreationResponse
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
        medicalRenewDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MedicalRenewDetailsViewModel::class.java)

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
                medicalRenewDetailsViewModel.paymentInquiry(medicalRenewalUI.oldRefId!!, deliveryType, address, mobile)
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
//        initializeViews()
        medicalRenewDetailsViewModel.paymentInquiry(medicalRenewalUI.oldRefId!!, deliveryType, address, mobile)
    }

    fun initializeViews() {
        llContent.visibility = View.VISIBLE
        val adapter = MedicalRenewPaymentItemsAdapter(dataBindingComponent, appExecutors) { }
        this.adapter = adapter
//        medicalRenewalPaymentUI.paymentItem = MedicalRenewalPaymentUI.PaymentItem(paymentRequestNumber = "111",amount = 555,name = "mona",paymentDetailsItems = null)
        medicalRenewalPaymentUI?.let {
            binding.medicalRenewalPayment = it

            it.paymentItem?.let {
                adapter.submitList(it.paymentDetailsItems)
            }
            bPay.visibility = if (it.paymentItem?.amount == null || it.paymentItem?.amount == 0) View.GONE else View.VISIBLE
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

    private fun handleViewState(state: MedicalRenewDetailsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading) {
            if (sendDecryptionKey) {
                llSuperProgressbar.visibility = View.INVISIBLE
                val paymentMethod = mechanismTypeButton.getText().toString()
                if (paymentMethod == getString(R.string.payment_card)) {
//                    navController().navigate(
//                            MedicalRenewDetailsFragmentDirections.openPayment(medicalRenewalPaymentUI, paymentCreationResponse.OriginalSenderRequestNumber,
//                                    paymentCreationResponse.CardRequestNumber, paymentCreationResponse.SessionId, paymentCreationResponse.TotalAuthorizationAmount.toString())
//                    )
                } else if (paymentMethod == getString(R.string.payment_channel) ||
                        paymentMethod == getString(R.string.payment_wallet) ||
                        paymentMethod == getString(R.string.payment_meeza)) {
//                    showAlert(getString(R.string.payment_reference) + "  " + paymentCreationResponse.CardRequestNumber) {
//                        navController().popBackStack()
//                        navController().navigate(R.id.homeFragment)
//                    }
//                    "senderRequestNumber" + response.OriginalSenderRequestNumber
                }
            } else {
                state.medicalRenewalPayment?.let {
                    medicalRenewalPaymentUI = it
//                    memberItem.paymentCreationRequest = it.paymentCreationRequest
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
        intent.putExtra(CowpayConstantKeys.Amount, medicalRenewalPaymentUI.paymentItem?.amount.toString())
        //user data
        intent.putExtra(CowpayConstantKeys.CustomerName, medicalRenewalPaymentUI.paymentItem?.name)
        intent.putExtra(CowpayConstantKeys.CustomerMobile, PreferencesHelper(requireContext()).mobile)
        intent.putExtra(CowpayConstantKeys.CustomerEmail, "customer@customer.com")
        //user id
        intent.putExtra(CowpayConstantKeys.CustomerMerchantProfileId, medicalRenewalPaymentUI.paymentItem?.paymentRequestNumber)


        startActivityForResult(intent, CowpayConstantKeys.PaymentMethodsActivityRequestCode)
    }

    fun createPayment() {
        try {

            paymentGateway = PaymentGateway(activity, "1234")
            paymentCreationRequest = PaymentCreationRequest()

            paymentCreationRequest.Sender.Id = "071"
            paymentCreationRequest.Sender.Name = "MSAD"
            paymentCreationRequest.Sender.Password = "1234"
            paymentCreationRequest.Description = "Test"
            paymentCreationRequest.SenderInvoiceNumber = medicalRenewalPaymentUI.paymentItem?.paymentRequestNumber.toString()
            paymentCreationRequest.AdditionalInfo = "Test"

            paymentCreationRequest.SenderRequestNumber = medicalRenewalPaymentUI.paymentItem?.paymentRequestNumber.toString()
            paymentCreationRequest.ServiceCode = "171"

            val settlementAmount = PaymentCreationRequest.SettlementAmount()

            settlementAmount.Amount = medicalRenewalPaymentUI.paymentItem?.amount!!.toDouble()
            settlementAmount.SettlementAccountCode = 647
            settlementAmount.Description = "Test"

            paymentCreationRequest.SettlementAmounts.add(settlementAmount)

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

//            paymentCreationRequest.RequestExpiryDate = medicalRenewalPaymentUI.paymentCreationRequest?.requestExpiryDate
            paymentCreationRequest.RequestExpiryDate = "2020-12-06"

            paymentCreationRequest.UserUniqueIdentifier = "12346743298546"

            val publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0MAVRsQax/cxZwZcfp+s0mzZtFGNRpuRLnyKUosdnlHn2JC1FSHpnmsON1Mp/SY+GR/Wk1kO2QGNNdszikPgAnN5/fcX6JPIkzSHCMLqPxUNqaCfn0eaLrwgsc1SCwlm+f8c+CseG3OeR+sUdq52PHf7edpjC60V4bNo/gEVRLV+VsvBde8jhet6Z/wRrKL5K1MQH0ByYn9upf96myRiTOSvocuBVHnlb2O+tapLVrNq7dMNXCHHB7IuNCFvP0f0QILeDW5CxebcsTItzxLLurKtA1lTWv+Ao9oqexy1BJzMytpS+BAz9kNzmt/g7RcdbXd0MxFotvoHjl2jwE1wLwIDAQAB"


//            medicalRenewDetailsViewModel.encryptData(paymentCreationRequest.Sender.Name, paymentCreationRequest.Sender.Password, paymentCreationRequest.serialize())
            paymentCreationRequest.serialize()

            val successCallback: ((response: PaymentCreationResponse) -> Unit) = { response ->
                sendDecryptionKey = true
                paymentCreationResponse = response

                if (mechanismTypeButton.getText().toString() == getString(R.string.payment_channel)) {

                    offlineSetPaid()
                } else {
                    llSuperProgressbar.visibility = View.INVISIBLE
                    val paymentMethod = mechanismTypeButton.getText().toString()
                    if (paymentMethod == getString(R.string.payment_card)) {
                        navController().navigate(
                                MedicalRenewDetailsFragmentDirections.openPayment(MemberUI(), paymentCreationResponse.OriginalSenderRequestNumber,
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
                }
//                medicalRenewDetailsViewModel.sendDecryptionKey(response.OriginalSenderRequestNumber, response.RequestDecryptionKey)
            }

            val failureCallback = {
                llSuperProgressbar.visibility = View.INVISIBLE
                showConnectionAlert(requireContext(), retryCallback = {
                    llContent.visibility = View.INVISIBLE
                    medicalRenewDetailsViewModel.paymentInquiry(medicalRenewalUI.oldRefId!!, deliveryType, address, mobile)
                }, cancelCallback = {
                    navController().navigateUp()
                })
            }

            paymentGateway.CreatePayment(paymentCreationRequest, "", publicKey, MobilePaymentCreationCallback(successCallback, failureCallback))
        } catch (ex: Exception) {
//            Log.i("Error", ex.message)
        }
    }

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

    fun offlineSetPaid() {

//        medicalRenewDetailsViewModel.setPaid(medicalRenewalPaymentUI.paymentItem?.paymentRequestNumber!!)
    }
    //endregion

    fun navController() = findNavController()
}
