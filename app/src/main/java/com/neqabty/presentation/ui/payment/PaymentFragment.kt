package com.neqabty.presentation.ui.payment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.efinance.mobilepaymentsdk.*
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.PaymentFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.payment_fragment.*
import javax.inject.Inject


class PaymentFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<PaymentFragmentBinding>()

    lateinit var paymentViewModel: PaymentViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var paymentGateway: PaymentGateway
    lateinit var paymentConfirmationRequest: PaymentConfirmationRequest
    var monthsList: List<String>? = mutableListOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    var yearsList: List<String>? = mutableListOf("2020", "2021", "2022", "2023", "2024", "2025", "2026")

    var month: String = ""
    var year: String = ""
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
        paymentViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PaymentViewModel::class.java)

        initializeViews()

        paymentViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        paymentViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                paymentViewModel.getSyndicates()
            }, cancelCallback = {
                navController().navigateUp()
            })
        })

//        paymentViewModel.getSyndicates()
    }

    private fun handleViewState(state: PaymentViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

    }

    fun initializeViews() {
//        val params = PaymentFragmentArgs.fromBundle(arguments!!)

        binding.spMonth.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, monthsList)
        binding.spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                month = parent.getItemAtPosition(position) as String
            }
        }

        binding.spYear.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, yearsList)
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


    fun confirmPayment() {
        val params = PaymentFragmentArgs.fromBundle(arguments!!)
        try {

            paymentGateway = PaymentGateway(activity, "1234")
            paymentConfirmationRequest = PaymentConfirmationRequest()

            paymentConfirmationRequest.Sender.Id = "077"
            paymentConfirmationRequest.Sender.Name = "MSAD"
            paymentConfirmationRequest.Sender.Password = "1234"

            paymentConfirmationRequest.SenderRequestNumber = params.senderRequestNumber

            paymentConfirmationRequest.SessionID = params.sessionID


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
            paymentConfirmationRequest.Card.CardNumber = "5111111111111118"
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


                showAlert(getString(R.string.payment_successful))
//                "senderRequestNumber"+ response.SenderRequestNumber
            }

            val failureCallback = {
                llSuperProgressbar.visibility = View.INVISIBLE
            }
            paymentGateway.ConfirmPayment(paymentConfirmationRequest, "", MobilePaymentConfirmationCallback(successCallback, failureCallback))

        } catch (ex: Exception) {
            Log.i("Error", ex.message)
        }

    }

//region

// endregion


    // region callback
    class MobilePaymentConfirmationCallback(private val successCallback: ((response: PaymentConfirmationResponse) -> Unit), private val failureCallback: (() -> Unit)) : PaymentConfirmationCallback {

        override fun onSuccess(response: PaymentConfirmationResponse) {
            Log.i("NEQABTY", "Request Completed Successfully")
            successCallback.invoke(response)
        }

        override fun onError(paymentException: PaymentException) {
            Log.e("NEQABTY", paymentException.details.message)
            failureCallback.invoke()
        }
    }

    // endregion

    fun navController() = findNavController()
}
