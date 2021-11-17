package com.neqabty.presentation.ui.onlinePharmacy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.OnlinePharmacyFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.util.autoCleared
//import com.neqabty.yodawy.modules.address.presentation.view.adressscreen.AddressesActivity
//import com.neqabty.yodawy.modules.address.presentation.view.homescreen.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnlinePharmacyFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<OnlinePharmacyFragmentBinding>()

    private val onlinePharmacyViewModel: OnlinePharmacyViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showAds(Constants.AD_ONLINE_PHARMACY)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.online_pharmacy_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViews()
    }

    private fun initializeViews(){
        binding.clVezeeta.setOnClickListener { navController().navigate(OnlinePharmacyFragmentDirections.openOnlinePharmacyVezeeta()) }
//        binding.clYodawy.setOnClickListener { startActivity(Intent(requireContext(), AddressesActivity::class.java)) }
    }

//region
// endregion
    fun navController() = findNavController()
}
