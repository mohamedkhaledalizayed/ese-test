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
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import com.neqabty.yodawy.modules.address.presentation.view.homescreen.HomeActivity
import com.neqabty.chefaa.modules.home.presentation.homescreen.ChefaaHomeActivity
import dagger.hilt.android.AndroidEntryPoint

var animationPlaybackSpeed: Double = 0.8
@AndroidEntryPoint
class OnlinePharmacyFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    private lateinit var pharmacyAdapter: PharmacyAdapter
    var binding by autoCleared<OnlinePharmacyFragmentBinding>()
    val listOfPharmacies: MutableList<Pharmacy> = mutableListOf()
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

    /**
     * Update RecyclerView Item Animation Durations
     */
    private fun updateRecyclerViewAnimDuration() = binding.pharmacyRecycler.itemAnimator?.run {
        removeDuration = loadingDuration * 60 / 100
        addDuration = loadingDuration
    }

    private val loadingDuration: Long
        get() = (600 / animationPlaybackSpeed).toLong()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView Init
        pharmacyAdapter = PharmacyAdapter(requireContext())
        binding.pharmacyRecycler.adapter = pharmacyAdapter
        binding.pharmacyRecycler.setHasFixedSize(true)
        updateRecyclerViewAnimDuration()

        pharmacyAdapter.onItemClickListener = object :
            PharmacyAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                if (id == 0){
                    val bundle = Bundle()
                    bundle.putString("user_number", PreferencesHelper(requireContext()).user)
                    bundle.putString("mobile_number", PreferencesHelper(requireContext()).mobile)
                    bundle.putString("jwt", PreferencesHelper(requireContext()).jwt)
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }else if (id==1){
                    navController().navigate(OnlinePharmacyFragmentDirections.openOnlinePharmacyVezeeta())
                }else{
                    val bundle = Bundle()
                    bundle.putString("user_number", PreferencesHelper(requireContext()).user)
                    bundle.putString("mobile_number", PreferencesHelper(requireContext()).mobile)
                    bundle.putString("jwt", PreferencesHelper(requireContext()).jwt)
                    val intent = Intent(requireContext(),  ChefaaHomeActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }
        }
        listOfPharmacies.clear()
        listOfPharmacies.add(Pharmacy(R.drawable.yodawy, getString(R.string.yodawy_title), getString(R.string.yodawy_details)))
        listOfPharmacies.add(Pharmacy(R.drawable.vezeeta, getString(R.string.vezeeta_title), getString(R.string.vezeeta_details)))
        listOfPharmacies.add(Pharmacy((R.drawable.pharmacy_bg),getString(R.string.chefaa_title),getString(R.string.yodawy_details)))


        pharmacyAdapter.submitList(listOfPharmacies)
    }

    private fun initializeViews(){
//        binding.clVezeeta.setOnClickListener { navController().navigate(OnlinePharmacyFragmentDirections.openOnlinePharmacyVezeeta()) }
//        binding.clYodawy.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("user_number", PreferencesHelper(requireContext()).user)
//            bundle.putString("mobile_number", PreferencesHelper(requireContext()).mobile)
//            bundle.putString("jwt", PreferencesHelper(requireContext()).jwt)
//            val intent = Intent(requireContext(), HomeActivity::class.java)
//            intent.putExtras(bundle)
//            startActivity(intent)
//        }
    }

//region
// endregion
    fun navController() = findNavController()
}
