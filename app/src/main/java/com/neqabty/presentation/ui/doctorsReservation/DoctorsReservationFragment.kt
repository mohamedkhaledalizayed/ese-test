package com.neqabty.presentation.ui.doctorsReservation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.DoctorsReservationFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.ui.onlinePharmacy.OnlinePharmacyFragmentDirections
import com.neqabty.presentation.ui.onlinePharmacy.Pharmacy
import com.neqabty.presentation.ui.onlinePharmacy.PharmacyAdapter
import com.neqabty.presentation.ui.onlinePharmacy.animationPlaybackSpeed
import com.neqabty.presentation.util.autoCleared
import com.neqabty.yodawy.modules.address.presentation.view.homescreen.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorsReservationFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    private lateinit var pharmacyAdapter: PharmacyAdapter

    var binding by autoCleared<DoctorsReservationFragmentBinding>()
    val listOfDoctors: MutableList<Pharmacy> = mutableListOf()

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.doctors_reservation_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }


    /**
     * Update RecyclerView Item Animation Durations
     */
    private fun updateRecyclerViewAnimDuration() = binding.rvDoctors.itemAnimator?.run {
        removeDuration = loadingDuration * 60 / 100
        addDuration = loadingDuration
    }

    private val loadingDuration: Long
        get() = (600 / animationPlaybackSpeed).toLong()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView Init
        pharmacyAdapter = PharmacyAdapter(requireContext())
        binding.rvDoctors.adapter = pharmacyAdapter
        binding.rvDoctors.setHasFixedSize(true)
        updateRecyclerViewAnimDuration()

        pharmacyAdapter.onItemClickListener = object :
            PharmacyAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                navController().navigate(DoctorsReservationFragmentDirections.openDoctorsReservationVezeeta())
            }
        }
        listOfDoctors.clear()
        listOfDoctors.add(Pharmacy(R.drawable.vezeeta, getString(R.string.vezeeta_title), getString(R.string.vezeeta_doctors_details)))

        pharmacyAdapter.submitList(listOfDoctors)
    }

//region

    // endregion
    fun navController() = findNavController()
}
