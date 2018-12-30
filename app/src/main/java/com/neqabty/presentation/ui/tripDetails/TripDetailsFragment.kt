package com.neqabty.presentation.ui.tripDetails

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
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting

@OpenForTesting
class TripDetailsFragment : BaseFragment(), Injectable {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<TripDetailsFragmentBinding>()

    lateinit var tripItem : TripUI

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        setupToolbar(navController())
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

        val params = TripDetailsFragmentArgs.fromBundle(arguments)
        tripItem = params.tripItem

        initializeViews()

    }

    fun initializeViews() {
        binding.tripItem = tripItem
    }


//region


// endregion

    fun navController() = findNavController()
}
