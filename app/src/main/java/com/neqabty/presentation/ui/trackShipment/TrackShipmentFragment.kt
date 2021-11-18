package com.neqabty.presentation.ui.trackShipment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.TrackShipmentFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrackShipmentFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<TrackShipmentFragmentBinding>()

    private val trackShipmentViewModel: TrackShipmentViewModel by viewModels()

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

        trackShipmentViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        trackShipmentViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                trackShipmentViewModel.trackShipment(sharedPref.user)
            }, cancelCallback = {
                navController().navigateUp()
                llSuperProgressbar.visibility = View.GONE
            }, message = error?.message)
        })

        trackShipmentViewModel.trackShipment(sharedPref.user)
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
