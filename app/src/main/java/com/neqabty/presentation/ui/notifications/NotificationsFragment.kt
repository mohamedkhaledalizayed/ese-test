package com.neqabty.presentation.ui.notifications

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
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.NotificationsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class NotificationsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<NotificationsFragmentBinding>()
    private var adapter by autoCleared<NotificationsAdapter>()

    lateinit var notificationsViewModel: NotificationsViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.notifications_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        notificationsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NotificationsViewModel::class.java)

        initializeViews()
        val adapter = NotificationsAdapter(dataBindingComponent, appExecutors) { notificationItem ->
            navController().navigate(
                    NotificationsFragmentDirections.notificationDetails(notificationItem.id.toString())
            )
        }
        this.adapter = adapter
        binding.rvNotifications.adapter = adapter

        notificationsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        notificationsViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                notificationsViewModel.getNotifications(PreferencesHelper(requireContext()).user, PreferencesHelper(requireContext()).subSyndicate.toString())
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        notificationsViewModel.getNotifications(PreferencesHelper(requireContext()).user, PreferencesHelper(requireContext()).subSyndicate.toString())
    }

    private fun handleViewState(state: NotificationsViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.notifications?.let {
            adapter.submitList(it)
        }
    }

    fun initializeViews() {
    }

    //region
// endregion

    fun navController() = findNavController()
}
