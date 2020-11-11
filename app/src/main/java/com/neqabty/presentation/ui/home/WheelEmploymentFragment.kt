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
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.wheel_payments_fragment.*
import javax.inject.Inject

class WheelEmploymentFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<WheelEmploymentFragmentBinding>()


    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.wheel_employment_fragment,
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
                navController().navigate(R.id.engineeringRecordsDetailsFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.RECORDS)
                navController().navigate(R.id.mobileFragment, bundle)
            }
        }

    }

    //region
    // endregion
    fun navController() = findNavController()
}
