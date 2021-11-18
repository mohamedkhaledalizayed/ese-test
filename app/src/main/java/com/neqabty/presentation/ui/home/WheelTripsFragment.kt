package com.neqabty.presentation.ui.home

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.Claiming1FragmentBinding
import com.neqabty.databinding.WheelNewsFragmentBinding
import com.neqabty.databinding.WheelTripsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.GovernUI
import com.neqabty.presentation.ui.news.NewsAdapter
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wheel_news_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class WheelTripsFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<WheelTripsFragmentBinding>()

    private val homeViewModel: HomeViewModel by viewModels()
    private var tripsAdapter by autoCleared<TripsAdapter>()

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.wheel_trips_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser)
            initializeViews()
    }

    private fun initializeViews() {
        homeViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })
        homeViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                homeViewModel.getTrips(sharedPref.mainSyndicate.toString())
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            }, message = error?.message)
        })
        llSuperProgressbar.visibility = View.GONE
//        homeViewModel.getTrips(sharedPref.mainSyndicate.toString())
    }

    private fun handleViewState(state: HomeViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        val tripsAdapter = TripsAdapter(dataBindingComponent, appExecutors) { tripsItem ->
            navController().navigate(
                    HomeFragmentDirections.tripDetails(tripsItem)
            )
        }
        this.tripsAdapter = tripsAdapter
        binding.rvTrips.adapter = tripsAdapter
        state.trips?.let {
            if (it.size >= 5) tripsAdapter.submitList(it.subList(0, 5)) else tripsAdapter.submitList(it)
            bSeemore.visibility = View.VISIBLE
        }

        bSeemore.setOnClickListener { navController().navigate(R.id.tripsFragment) }

    }
//region
    // endregion
fun navController() = findNavController()
}
