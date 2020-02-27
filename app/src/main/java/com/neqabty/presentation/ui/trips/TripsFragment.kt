package com.neqabty.presentation.ui.trips

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
import com.neqabty.databinding.TripsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject

class TripsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<TripsFragmentBinding>()
    private var adapter by autoCleared<TripsAdapter>()

    lateinit var tripsViewModel: TripsViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

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

        val adapter = com.neqabty.presentation.ui.trips.TripsAdapter(dataBindingComponent, appExecutors) { trip ->
            navController().navigate(
                    TripsFragmentDirections.tripDetails(trip))
        }
        this.adapter = adapter
        binding.rvTrips.adapter = adapter

        tripsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        tripsViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                tripsViewModel.getTrips(PreferencesHelper(requireContext()).mainSyndicate.toString())
            }, cancelCallback = {
                navController().navigateUp()
            })
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
