package com.neqabty.presentation.ui.payment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.efinance.mobilepaymentsdk.*
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.PaymentFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.payment_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class PaymentFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<PaymentFragmentBinding>()

    private val paymentViewModel: PaymentViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var paymentGateway: PaymentGateway
    lateinit var paymentConfirmationRequest: PaymentConfirmationRequest
    var monthsList: List<String>? = mutableListOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    var yearsList: List<String>? = mutableListOf("20", "21", "22", "23", "24", "25", "26")

    var month: String = ""
    var year: String = ""

    lateinit var params: PaymentFragmentArgs
    lateinit var medicalRenewalPaymentUI: MedicalRenewalPaymentUI

    var requestNumber = "0"
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.payment_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        paymentViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        paymentViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        params = PaymentFragmentArgs.fromBundle(arguments!!)
        medicalRenewalPaymentUI = params.medicalRenewalPaymentUI
        initializeViews()
    }

    private fun handleViewState(state: PaymentViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if(state.hash.equals("123")){
        showAlert(getString(R.string.payment_successful)) {
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }}
    }

    fun initializeViews() {

        edCVV.setText("123")
        edNumber.setText("5111111111111118")
        edName.setText("Ahmed Mohamed")
        binding.spMonth.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, monthsList!!)
        binding.spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                month = parent.getItemAtPosition(position) as String
            }
        }

        binding.spYear.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, yearsList!!)
        binding.spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                year = parent.getItemAtPosition(position) as String
            }
        }
        bConfirm.setOnClickListener {
            llSuperProgressbar.visibility = View.VISIBLE
            confirmPayment()
        }
    }

    private fun confirmPayment() {
        val params = PaymentFragmentArgs.fromBundle(arguments!!)
        try {

//static set
            var id = "071"
            var name = "MSAD"
            var password = "1234"

            paymentGateway = PaymentGateway(activity, password)
            paymentConfirmationRequest = PaymentConfirmationRequest()

            paymentConfirmationRequest.Sender.Id = id
//            paymentConfirmationRequest.Sender.Id = "077"
            paymentConfirmationRequest.Sender.Name = name
//            paymentConfirmationRequest.Sender.Name = "MSAD"
            paymentConfirmationRequest.Sender.Password = password

            requestNumber = params.senderRequestNumber
            paymentConfirmationRequest.SenderRequestNumber = params.senderRequestNumber

            paymentConfirmationRequest.SessionID = params.sessionID
            ///////////
//            paymentGateway = PaymentGateway(activity, memberItem.paymentCreationRequest?.sender?.password)
//            paymentConfirmationRequest = PaymentConfirmationRequest()
//
//            paymentConfirmationRequest.Sender.Id = memberItem.paymentCreationRequest?.sender?.id
////            paymentConfirmationRequest.Sender.Id = "077"
//            paymentConfirmationRequest.Sender.Name = memberItem.paymentCreationRequest?.sender?.name
////            paymentConfirmationRequest.Sender.Name = "MSAD"
//            paymentConfirmationRequest.Sender.Password = memberItem.paymentCreationRequest?.sender?.password
//
//            paymentConfirmationRequest.SenderRequestNumber = params.senderRequestNumber
//
//            paymentConfirmationRequest.SessionID = params.sessionID

            /**
             * Test Cards
             *
             * CardNumber = "5111111111111118";  //3D Secure Not Enrolled
             * CardNumber = "5123450000000008";  //3D Secure Enrolled
             *
             * CardCVV = "123";
             * CardExpiryMonth = "07";
             * CardExpiryYear = "20";
             *
             *
             * */

            paymentConfirmationRequest.Card.NameOnCard = edName.text.toString()
            paymentConfirmationRequest.Card.CardNumber = edNumber.text.toString()
            paymentConfirmationRequest.Card.CardCVV = edCVV.text.toString()
            paymentConfirmationRequest.Card.CardExpiryMonth = spMonth.selectedItem.toString()
            paymentConfirmationRequest.Card.CardExpiryYear = spYear.selectedItem.toString()
            paymentConfirmationRequest.Card.SaveCardFlag = true

            paymentConfirmationRequest.CardToken = ""

            paymentConfirmationRequest.Amount = params.amount.toDouble()

            paymentConfirmationRequest.CardRequestNumber = params.cardRequestNumber

            paymentConfirmationRequest.serialize()

            val successCallback: ((response: PaymentConfirmationResponse) -> Unit) = { response ->

                llSuperProgressbar.visibility = View.INVISIBLE
//                offlineSetPaid()
                "senderRequestNumber"+ response.SenderRequestNumber
            }

            val failureCallback = {
                llSuperProgressbar.visibility = View.INVISIBLE
                showConnectionAlert(requireContext(), retryCallback = {
                    navController().navigateUp()
                }, cancelCallback = {
                    navController().navigateUp()
                })
            }
            paymentGateway.ConfirmPayment(paymentConfirmationRequest, "", MobilePaymentConfirmationCallback(successCallback, failureCallback))
        } catch (ex: Exception) {
//            Log.i("Error", ex.message)
        }
    }

//region

// endregion

    // region callback
    class MobilePaymentConfirmationCallback(
            private val successCallback: ((response: PaymentConfirmationResponse) -> Unit),
        private val failureCallback: (() -> Unit)
    ) : PaymentConfirmationCallback {

        override fun onSuccess(response: PaymentConfirmationResponse) {
//            Log.i("NEQABTY", "Request Completed Successfully")
            successCallback.invoke(response)
        }

        override fun onError(paymentException: PaymentException) {
//            Log.e("NEQABTY", paymentException.details.message)
            failureCallback.invoke()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10000)
                paymentGateway.handle3DSecureAuthenticationResult(requestCode, resultCode, data)
        }
    }

//    fun offlineSetPaid() {
//        paymentViewModel.setPaid(requestNumber)
//    }
    // endregion

    fun navController() = findNavController()
}
