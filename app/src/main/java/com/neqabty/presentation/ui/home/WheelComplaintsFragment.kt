package com.neqabty.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.WheelComplaintsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wheel_payments_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class WheelComplaintsFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<WheelComplaintsFragmentBinding>()


    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.wheel_complaints_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bGo.setOnClickListener {
//            Toast.makeText(requireContext(), getString(R.string.closed_complaints), Toast.LENGTH_SHORT).show()

            if (sharedPref.isRegistered)
                navController().navigate(R.id.complaintsFragment)
            else {
                    val bundle: Bundle = Bundle()
                    bundle.putInt("type", Constants.COMPLAINTS)
                    navController().navigate(R.id.signupFragment, bundle)
            }
        }

    }

    //region
    // endregion
    fun navController() = findNavController()
}
