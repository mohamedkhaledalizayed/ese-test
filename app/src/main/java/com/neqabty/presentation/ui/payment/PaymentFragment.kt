package com.neqabty.presentation.ui.payment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.efinance.mobilepaymentsdk.PaymentConfirmationCallback
import com.efinance.mobilepaymentsdk.PaymentConfirmationResponse
import com.efinance.mobilepaymentsdk.PaymentException
import com.neqabty.AppExecutors
import com.neqabty.MainActivity
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
        bConfirm.setOnClickListener {
            //            createPayment()
        }
    }

//region

// endregion


// region callback
class MobilePaymentConfirmationCallback(private val context: Context) : PaymentConfirmationCallback {

    override fun onSuccess(response: PaymentConfirmationResponse) {
        Log.i("NEQABTY", "Request Completed Successfully")

        Toast.makeText(context, "Payment Confirmed Successfully", Toast.LENGTH_LONG).show()

        //        Intent intent = new Intent(context, PaymentStatusInquiryActivity.class);
        val intent = Intent(context, MainActivity::class.java)

        intent.putExtra("senderRequestNumber", response.SenderRequestNumber)

        context.startActivity(intent)

    }

    override fun onError(paymentException: PaymentException) {
        Log.e("NEQABTY", paymentException.details.message)


    }
}

    // endregion

    fun navController() = findNavController()
}
