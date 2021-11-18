package com.neqabty.presentation.ui.tripDetails

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
import com.neqabty.databinding.TripDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.TripUI
import com.neqabty.presentation.ui.common.CustomImagePagerAdapter
import com.neqabty.presentation.ui.trips.TripsData
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TripDetailsFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<TripDetailsFragmentBinding>()

    var tripId: Int = 0

    private val tripDetailsViewModel: TripDetailsViewModel by viewModels()

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

        tripDetailsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        tripDetailsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                tripDetailsViewModel.getTripDetails(tripId.toString())
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
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
        tripItem.title?.let { setToolbarTitle(it) }
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

        binding.bViewRegiments.visibility = if(tripItem.counter!! > 0) View.VISIBLE else View.INVISIBLE
        binding.bViewRegiments.setOnClickListener {
            TripsData.tripItem = tripItem
            if (sharedPref.isRegistered)
                navController().navigate(TripDetailsFragmentDirections.openTripReservation(tripItem))
            else
                navController().navigate(TripDetailsFragmentDirections.openLogin(2))
        }
    }

//region

// endregion

    fun navController() = findNavController()
}
