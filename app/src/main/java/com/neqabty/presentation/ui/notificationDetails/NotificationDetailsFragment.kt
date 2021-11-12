package com.neqabty.presentation.ui.notificationDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.NotificationDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.entities.NotificationUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationDetailsFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<NotificationDetailsFragmentBinding>()

    lateinit var notificationId: String
    var serviceId: Int = 1
    private val notificationDetailsViewModel: NotificationDetailsViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.notification_details_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = NotificationDetailsFragmentArgs.fromBundle(arguments!!)
        notificationId = params.notificationId
        serviceId = params.serviceId.toInt()

        notificationDetailsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        notificationDetailsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                notificationDetailsViewModel.getNotificationDetails(serviceId, serviceId, PreferencesHelper(requireContext()).user, notificationId)
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        notificationDetailsViewModel.getNotificationDetails(serviceId, serviceId, PreferencesHelper(requireContext()).user, notificationId)
    }

    fun initializeViews(notificationItem: NotificationUI) {
        binding.notificationItem = notificationItem
        binding.bViewAttachment.setOnClickListener {
            val attachmentIntent = Intent(Intent.ACTION_VIEW, Uri.parse(notificationItem.approvalImage))
            startActivity(attachmentIntent)
        }
        binding.bPay.setOnClickListener {
            navController().navigate(
                    NotificationDetailsFragmentDirections.openInquiryDetailsFragment(notificationItem.userNumber!!, 1, notificationItem.notificationType!!, MedicalRenewalPaymentUI(requestID = notificationItem.approvalNumber!!, paymentItem = MedicalRenewalPaymentUI.PaymentItem(paymentRequestNumber = notificationItem.approvalNumber!!, amount = notificationItem.cost!!, engNumber = notificationItem.userNumber.toString(), engName = notificationItem.name!!, name = notificationItem.name!!)), if (notificationItem.notificationTypeID == 2) Constants.PAYMENT_TYPE_TRIPS else Constants.PAYMENT_TYPE_RECORDS) // TODO serviceID, 2 trips 3 records
            )
        }
    }

    private fun handleViewState(state: NotificationDetailsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.llContainer.visibility = if (state.isLoading) View.GONE else View.VISIBLE
        state.notification?.let {
            initializeViews(it)
        }
    }
//region

// endregion

    fun navController() = findNavController()
}
