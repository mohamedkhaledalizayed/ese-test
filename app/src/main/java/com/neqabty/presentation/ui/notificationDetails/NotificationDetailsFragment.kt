package com.neqabty.presentation.ui.notificationDetails

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.NotificationDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.entities.NotificationUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import javax.inject.Inject

class NotificationDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<NotificationDetailsFragmentBinding>()

    lateinit var notificationId: String
    var serviceId: Int = 1
    lateinit var notificationDetailsViewModel: NotificationDetailsViewModel

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

        notificationDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NotificationDetailsViewModel::class.java)

        notificationDetailsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        notificationDetailsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                notificationDetailsViewModel.getNotificationDetails(serviceId, serviceId, PreferencesHelper(requireContext()).user, notificationId.toInt())
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        notificationDetailsViewModel.getNotificationDetails(serviceId, serviceId, PreferencesHelper(requireContext()).user, notificationId.toInt())
    }

    fun initializeViews(notificationItem: NotificationUI) {
        binding.notificationItem = notificationItem
        binding.bViewAttachment.setOnClickListener {
            val attachmentIntent = Intent(Intent.ACTION_VIEW, Uri.parse(notificationItem.approvalImage))
            startActivity(attachmentIntent)
        }
        binding.bPay.setOnClickListener {
            navController().navigate(
                    NotificationDetailsFragmentDirections.openInquiryDetailsFragment(1, notificationItem.notificationType!!, MemberUI(amount = notificationItem.cost!!, requestID = notificationItem.approvalNumber!!, engineerNumber = notificationItem.userNumber.toString(), engineerName = notificationItem.name!!), if (notificationItem.notificationTypeID == 2) Constants.PAYMENT_TYPE_TRIPS else Constants.PAYMENT_TYPE_RECORDS) // TODO serviceID, 2 trips 3 records
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
