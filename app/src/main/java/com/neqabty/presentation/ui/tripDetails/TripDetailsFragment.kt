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
import com.neqabty.R
import com.neqabty.databinding.TripDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.TripUI
import com.neqabty.presentation.ui.common.CustomImagePagerAdapter
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class TripDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<TripDetailsFragmentBinding>()

    var tripId: Int = 0

    lateinit var tripDetailsViewModel: TripDetailsViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
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
                binding.progressbar.visibility = View.VISIBLE
                tripDetailsViewModel.getTripDetails(tripId.toString())
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        tripDetailsViewModel.getTripDetails(tripId.toString())
    }

    private fun handleViewState(state: TripDetailsViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.trip?.let {
            initializeViews(it)
        }
    }

    fun initializeViews(tripItem: TripUI) {
        tripItem.imgs?.let {
            var imgs = mutableListOf<String>()
            for (i in 0..it.size) {
                imgs[i] = it[i].file!!
            }
            val adapter = CustomImagePagerAdapter(requireContext(), imgs)
            binding.viewpager.adapter = adapter
            binding.viewpager.setSwipePagingEnabled(true)
            binding.viewpager.visibility = View.VISIBLE
            binding.indicator.setViewPager(binding.viewpager)
            binding.indicator.visibility = View.VISIBLE
        }

        binding.tvPrice.visibility = View.VISIBLE
        binding.tripItem = tripItem
    }

//region


// endregion

    fun navController() = findNavController()
}