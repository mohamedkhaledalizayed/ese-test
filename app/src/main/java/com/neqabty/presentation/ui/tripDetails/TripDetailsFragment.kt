package com.neqabty.presentation.ui.tripDetails

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
import com.neqabty.databinding.TripDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.GovernUI
import com.neqabty.presentation.entities.TripUI
import com.neqabty.presentation.ui.claiming.ClaimingData
import com.neqabty.presentation.ui.common.CustomImagePagerAdapter
import com.neqabty.presentation.ui.trips.TripsData
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.claiming1_fragment.*

import javax.inject.Inject

class TripDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<TripDetailsFragmentBinding>()
    private var adapter by autoCleared<RegimentsAdapter>()

    var tripId: Int = 0

    lateinit var tripDetailsViewModel: TripDetailsViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.trip_details_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = TripDetailsFragmentArgs.fromBundle(arguments!!)
        tripId = params.tripItem.id

        tripDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TripDetailsViewModel::class.java)

        tripDetailsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        tripDetailsViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                tripDetailsViewModel.getTripDetails(tripId.toString())
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        tripDetailsViewModel.getTripDetails(tripId.toString())
    }

    private fun handleViewState(state: TripDetailsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.trip?.let {
            initializeViews(it)
        }
    }

    fun initializeViews(tripItem: TripUI) {
        tripItem.imgs?.let {
            //            var imgs = mutableListOf<String>()
//            for (i in 0 until it.size) {
//                imgs.add(it[i].file!!)
//            }
            val adapter = CustomImagePagerAdapter(requireContext(), it)
            binding.viewpager.adapter = adapter
            binding.viewpager.setSwipePagingEnabled(true)
            binding.viewpager.visibility = View.VISIBLE
            binding.indicator.setViewPager(binding.viewpager)
            binding.indicator.visibility = View.VISIBLE
        }

        binding.svContent.visibility = View.VISIBLE
        binding.tripItem = tripItem

        val adapter = RegimentsAdapter(dataBindingComponent, appExecutors) {}
        this.adapter = adapter
        binding.rvRegiments.adapter = adapter
        adapter.submitList(tripItem.regiments)

        binding.bReserve.setOnClickListener {
            TripsData.tripItem = tripItem
            if (PreferencesHelper(requireContext()).isRegistered)
                navController().navigate(TripDetailsFragmentDirections.openTripReservation(tripItem))
            else
                navController().navigate(TripDetailsFragmentDirections.openLogin(2))
        }
    }

//region

// endregion

    fun navController() = findNavController()
}
