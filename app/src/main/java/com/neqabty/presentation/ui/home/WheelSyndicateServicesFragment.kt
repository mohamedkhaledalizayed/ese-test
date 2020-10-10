package com.neqabty.presentation.ui.home

import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.WheelEmploymentFragmentBinding
import com.neqabty.databinding.WheelPaymentsFragmentBinding
import com.neqabty.databinding.WheelRetireesFragmentBinding
import com.neqabty.databinding.WheelSyndicateServicesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.wheel_payments_fragment.*
import javax.inject.Inject

class WheelSyndicateServicesFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<WheelSyndicateServicesFragmentBinding>()


    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.wheel_syndicate_services_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bGo.setOnClickListener {
            if (PreferencesHelper(requireContext()).isRegistered)
                navController().navigate(R.id.updateDataVerificationFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", 4)
                navController().navigate(R.id.mobileFragment, bundle)
            }
        }

    }

    //region
    // endregion
    fun navController() = findNavController()
}
