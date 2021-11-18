package com.neqabty.presentation.ui.notifications

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.NotificationsListFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsListFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<NotificationsListFragmentBinding>()
    private var adapter by autoCleared<NotificationsAdapter>()

    private val notificationsViewModel: NotificationsViewModel by viewModels()

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

        val adapter = NotificationsAdapter(dataBindingComponent, appExecutors) { notificationItem ->
            navController().navigate(
                    NotificationsFragmentDirections.notificationDetails(notificationItem.id.toString(), typeID.toString())
            )
        }
        this.adapter = adapter
        binding.rvNotifications.adapter = adapter

        notificationsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        notificationsViewModel.errorState.observe(this, Observer { error ->
            binding.tvError.visibility = View.VISIBLE
            binding.rvNotifications.visibility = View.GONE
//            showConnectionAlert(requireContext(), retryCallback = {
//                llSuperProgressbar.visibility = View.VISIBLE
//                loadNotifications(typeID)
//            }, cancelCallback = {
//                navController().navigateUp()
//            }, message = error?.message)
        })
        loadNotifications(typeID)
        initializeViews()
    }

    private fun handleViewState(state: NotificationsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
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
        llSuperProgressbar.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
        notificationsViewModel.getNotifications(typeID, 2, sharedPref.user)
    }

    //region
// endregion

    fun navController() = findNavController()
}
