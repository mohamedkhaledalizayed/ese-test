package com.neqabty.presentation.ui.trackShipment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.TrackShipmentFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import javax.inject.Inject


class TrackShipmentFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<TrackShipmentFragmentBinding>()

    lateinit var trackShipmentViewModel: TrackShipmentViewModel

    @Inject
    lateinit var appExecutors: AppExecutors
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.track_shipment_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        trackShipmentViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TrackShipmentViewModel::class.java)

        trackShipmentViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        trackShipmentViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                trackShipmentViewModel.trackShipment(PreferencesHelper(requireContext()).user)
            }, cancelCallback = {
                navController().navigateUp()
                llSuperProgressbar.visibility = View.GONE
            }, message = error?.message)
        })

        trackShipmentViewModel.trackShipment(PreferencesHelper(requireContext()).user)
//        trackShipmentViewModel.trackShipment("7007439")

    }

    fun initializeViews() {
        val adapter = ShipmentsAdapter(dataBindingComponent, appExecutors) {}
        binding.rvShipments.adapter = adapter
        (binding.rvShipments.adapter as ShipmentsAdapter).submitList(trackShipmentViewModel.viewState.value?.trackShipmentList)
        (binding.rvShipments.adapter as ShipmentsAdapter).notifyDataSetChanged()
    }

    private fun handleViewState(state: TrackShipmentViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.trackShipmentList?.let {
            initializeViews()
        }
    }

//region
// endregion

    fun navController() = findNavController()
}
