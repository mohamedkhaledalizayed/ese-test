package com.neqabty.presentation.ui.trips

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.TripsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject

class TripsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<TripsFragmentBinding>()
    private var adapter by autoCleared<com.neqabty.presentation.ui.home.TripsAdapter>()

    lateinit var tripsViewModel: TripsViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showAds(Constants.AD_TRIPS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.trips_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tripsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TripsViewModel::class.java)

        initializeViews()

        val adapter = com.neqabty.presentation.ui.home.TripsAdapter(dataBindingComponent, appExecutors) { trip ->
            navController().navigate(
                    TripsFragmentDirections.tripDetails(trip))
        }
        this.adapter = adapter
        binding.rvTrips.adapter = adapter

        tripsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        tripsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                tripsViewModel.getTrips(PreferencesHelper(requireContext()).mainSyndicate.toString())
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        tripsViewModel.getTrips(PreferencesHelper(requireContext()).mainSyndicate.toString())
    }

    private fun handleViewState(state: TripsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.trips?.let {
            adapter.submitList(it)
        }
    }

    fun initializeViews() {
    }

//region

// endregion

    fun navController() = findNavController()
}
