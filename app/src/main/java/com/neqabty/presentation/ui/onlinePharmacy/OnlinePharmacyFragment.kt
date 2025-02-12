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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.OnlinePharmacyFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.util.autoCleared
import com.neqabty.yodawy.modules.address.presentation.view.homescreen.HomeActivity
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llSuperProgressbar.visibility = View.VISIBLE
        // RecyclerView Init
        pharmacyAdapter = PharmacyAdapter(requireContext())
        binding.pharmacyRecycler.adapter = pharmacyAdapter
        binding.pharmacyRecycler.setHasFixedSize(true)
        updateRecyclerViewAnimDuration()

        pharmacyAdapter.onItemClickListener = object :
            PharmacyAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                if (id == 1){
                    val bundle = Bundle()
                    bundle.putString("user_number", sharedPref.user)
                    bundle.putString("mobile_number", sharedPref.mobile)
                    bundle.putString("jwt", sharedPref.jwt)
                    bundle.putString("fixed_token", Constants.YODAWY_CONFIG.value?.publicKey)
                    bundle.putBoolean("total_amount", Constants.YODAWY_CONFIG.value?.totalAmount ?: false)
                    bundle.putString("delivery_sentence", Constants.YODAWY_CONFIG.value?.deliverySentence)
                    bundle.putString("url", Constants.YODAWY_CONFIG.value?.url)
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }else{
                    navController().navigate(OnlinePharmacyFragmentDirections.openOnlinePharmacyVezeeta())
                }
            }
        }
        listOfPharmacies.clear()
        listOfPharmacies.add(Pharmacy(R.drawable.vezeeta, getString(R.string.vezeeta_title), getString(R.string.vezeeta_details)))
        setupYodawy()

        pharmacyAdapter.submitList(listOfPharmacies)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViews()
    }

    private fun initializeViews(){
        showBannerAd(Constants.AD_ONLINE_PHARMACY, binding.ivBanner)
    }

//region
    private fun setupYodawy() {
        Constants.YODAWY_CONFIG.value?.let {
            if (Constants.YODAWY_CONFIG.value?.status == true)
                listOfPharmacies.add( Pharmacy(
                    R.drawable.yodawy,
                    getString(R.string.yodawy_title),
                    getString(R.string.yodawy_details)
                )
            )

            llSuperProgressbar.visibility = View.GONE
            return
        }

        Constants.YODAWY_CONFIG.observe(viewLifecycleOwner, Observer {
            if (Constants.YODAWY_CONFIG.value?.status == true)
                listOfPharmacies.add( Pharmacy(
                    R.drawable.yodawy,
                    getString(R.string.yodawy_title),
                    getString(R.string.yodawy_details)
                )
            )
            llSuperProgressbar.visibility = View.GONE
            pharmacyAdapter.submitList(listOfPharmacies)
        })
    }
// endregion
    fun navController() = findNavController()
}
