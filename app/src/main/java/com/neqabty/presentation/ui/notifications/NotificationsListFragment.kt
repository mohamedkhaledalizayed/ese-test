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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.NotificationsFragmentBinding
import com.neqabty.databinding.NotificationsListFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.GovernUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject

class NotificationsListFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<NotificationsListFragmentBinding>()
    private var adapter by autoCleared<NotificationsAdapter>()

    lateinit var notificationsViewModel: NotificationsViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    var typeID = 2

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.notifications_list_fragment,
                container,
                false,
                dataBindingComponent
        )

        val bundle = this.arguments
        bundle?.let { typeID = it.getInt("typeID") }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        notificationsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NotificationsViewModel::class.java)

        val adapter = NotificationsAdapter(dataBindingComponent, appExecutors) { notificationItem ->
            navController().navigate(
                    NotificationsFragmentDirections.notificationDetails(notificationItem.id.toString(),typeID.toString())
            )
        }
        this.adapter = adapter
        binding.rvNotifications.adapter = adapter

        notificationsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        notificationsViewModel.errorState.observe(this, Observer { _ ->
            binding.tvError.visibility = View.VISIBLE
            binding.rvNotifications.visibility = View.GONE
//            showConnectionAlert(requireContext(), retryCallback = {
//                binding.progressbar.visibility = View.VISIBLE
//                loadNotifications(typeID)
//            }, cancelCallback = {
//                navController().navigateUp()
//            })
        })
        loadNotifications(typeID)
        initializeViews()
    }

    private fun handleViewState(state: NotificationsViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.notifications?.let {
            binding.tvError.visibility = View.GONE
            binding.rvNotifications.visibility = View.VISIBLE
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
    }

    fun initializeViews() {
    }

    fun loadNotifications(typeID: Int) {
        binding.progressbar.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
        notificationsViewModel.getNotifications(typeID, 2, PreferencesHelper(requireContext()).user.toInt())
    }

    //region
// endregion

    fun navController() = findNavController()
}
