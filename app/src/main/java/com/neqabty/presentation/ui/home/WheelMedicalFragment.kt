package com.neqabty.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.WheelMedicalFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wheel_medical_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class WheelMedicalFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<WheelMedicalFragmentBinding>()


    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.wheel_medical_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ibDirectory.setOnClickListener { navController().navigate(R.id.chooseAreaFragment) }
        ibClaiming.setOnClickListener {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.claimingFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.CLAIMING)
                navController().navigate(R.id.signupFragment, bundle)
            }
        }
        ibRenew.setOnClickListener {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.medicalRenewFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.MEDICAL_RENEW)
                navController().navigate(R.id.signupFragment, bundle)
            }
        }
        ibComplaints.setOnClickListener {
//            Toast.makeText(requireContext(), getString(R.string.closed_complaints), Toast.LENGTH_SHORT).show()

            if (sharedPref.isRegistered)
                navController().navigate(R.id.medicalComplaintsFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.MEDICAL_COMPLAINTS)
                navController().navigate(R.id.signupFragment, bundle)
            }
        }
        ibOnlinePharmacy.setOnClickListener {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.onlinePharmacyFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.ONLINE_PHARMACY)
                navController().navigate(R.id.signupFragment, bundle)
            }
        }
        ibMedicalLetters.setOnClickListener {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.medicalLettersFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.MEDICAL_LETTERS)
                navController().navigate(R.id.signupFragment, bundle)
            }
        }
        ibDoctorsReservation.setOnClickListener {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.doctorsReservationFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.DOCTORS_RESERVATION)
                navController().navigate(R.id.signupFragment, bundle)
            }
        }

        setupDoctorsReservation()
    }

    fun setupDoctorsReservation() {
        Constants.VEZEETA_CONFIG?.value?.let {
            ibDoctorsReservation.visibility = if (it.status) View.VISIBLE else View.GONE
            return
        }
        Constants.VEZEETA_CONFIG?.observe(viewLifecycleOwner, Observer {
            if (it.status)
                ibDoctorsReservation.visibility = View.VISIBLE
        })
    }

    //region
    // endregion
    fun navController() = findNavController()
}
