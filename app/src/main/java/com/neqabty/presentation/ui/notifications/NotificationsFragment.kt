package com.neqabty.presentation.ui.notifications

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
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
import kotlinx.android.synthetic.main.notifications_fragment.*
import javax.inject.Inject

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

        val adapter = NotificationsAdapter(dataBindingComponent, appExecutors) { notificationItem ->
            navController().navigate(
                    NotificationsFragmentDirections.notificationDetails(notificationItem.id.toString(),notificationItem.notificationTypeID.toString())
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
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.notifications?.let {
            binding.tvError.visibility = View.GONE
            binding.rvNotifications.visibility = View.VISIBLE
            (rvNotifications.adapter as NotificationsAdapter).submitList(it)
            (rvNotifications.adapter as NotificationsAdapter).notifyDataSetChanged()
        }
    }


    fun loadNotifications(typeID: Int) {
        binding.progressbar.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
        notificationsViewModel.getNotifications(typeID, 0, PreferencesHelper(requireContext()).user.toInt())
    }

    //region
// endregion

    fun navController() = findNavController()
}
