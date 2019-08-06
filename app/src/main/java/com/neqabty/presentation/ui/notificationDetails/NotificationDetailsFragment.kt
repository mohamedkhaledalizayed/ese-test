package com.neqabty.presentation.ui.notificationDetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.NotificationDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
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
        notificationDetailsViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                notificationDetailsViewModel.getNotificationDetails(serviceId, 1, PreferencesHelper(requireContext()).user.toInt(), notificationId.toInt())
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        notificationDetailsViewModel.getNotificationDetails(serviceId, 1, PreferencesHelper(requireContext()).user.toInt(), notificationId.toInt())
    }

    fun initializeViews(notificationItem: NotificationUI) {
        binding.notificationItem = notificationItem
    }

    private fun handleViewState(state: NotificationDetailsViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.llContainer.visibility = if (state.isLoading) View.GONE else View.VISIBLE
        state.notification?.let {
            initializeViews(it)
        }
    }
//region

// endregion

    fun navController() = findNavController()
}
