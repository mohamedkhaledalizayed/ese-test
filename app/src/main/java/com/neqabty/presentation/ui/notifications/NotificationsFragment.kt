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
import com.neqabty.databinding.NotificationsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.notifications_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<NotificationsFragmentBinding>()

    private var adapter by autoCleared<NotificationsAdapter>()

    private val notificationsViewModel: NotificationsViewModel by viewModels()

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
        val adapter = NotificationsAdapter(dataBindingComponent, appExecutors) { notificationItem ->
            navController().navigate(
                    NotificationsFragmentDirections.notificationDetails(notificationItem.id.toString(), notificationItem.notificationTypeID.toString())
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
        loadNotifications(1)
        initializeViews()

//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.trips)))
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.claiming_title)))
//        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
//
//        val adapter = NotificationsTabAdapter(context!!, childFragmentManager, tabLayout!!.tabCount)
//        viewPager!!.adapter = adapter
//
//        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
//
//        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                viewPager!!.currentItem = tab.position
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab) {
//
//            }
//        })
//
//        val tab = binding.tabLayout.getTabAt(0)
//        tab?.select()
//        viewPager.currentItem = 1
    }

    fun initializeViews() {
    }

    private fun handleViewState(state: NotificationsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.notifications?.let {
            binding.tvError.visibility = View.GONE
            binding.rvNotifications.visibility = View.VISIBLE
            (rvNotifications.adapter as NotificationsAdapter).submitList(it)
            (rvNotifications.adapter as NotificationsAdapter).notifyDataSetChanged()
        }
    }

    fun loadNotifications(typeID: Int) {
        llSuperProgressbar.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
        notificationsViewModel.getNotifications(typeID, 0, PreferencesHelper(requireContext()).user)
    }

    //region
// endregion

    fun navController() = findNavController()
}
