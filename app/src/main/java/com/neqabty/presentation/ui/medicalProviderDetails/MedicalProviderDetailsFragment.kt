package com.neqabty.presentation.ui.medicalProviderDetails

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalProviderDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.ui.phones.PhonesAdapter
import com.neqabty.presentation.ui.phones.PhonesFragment
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import com.neqabty.presentation.util.openMap
import kotlinx.android.synthetic.main.medical_provider_details_fragment.*

import javax.inject.Inject

class MedicalProviderDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<MedicalProviderDetailsFragmentBinding>()
    lateinit var providerItem: ProviderUI

    lateinit var medicalProviderDetailsViewModel: MedicalProviderDetailsViewModel
    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var state: MedicalProviderDetailsViewState
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_provider_details_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        medicalProviderDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MedicalProviderDetailsViewModel::class.java)

        val params = MedicalProviderDetailsFragmentArgs.fromBundle(requireArguments())
        providerItem = params.medicalProviderItem
        medicalProviderDetailsViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })

        medicalProviderDetailsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                medicalProviderDetailsViewModel.isFavorite(providerItem)
                medicalProviderDetailsViewModel.getProviderDetails(providerItem.id.toString(), providerItem.typeID!!)
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        medicalProviderDetailsViewModel.isFavorite(providerItem)
        medicalProviderDetailsViewModel.getProviderDetails(providerItem.id.toString(), providerItem.typeID!!)
    }

    private fun handleViewState(state: MedicalProviderDetailsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.clHolder.visibility = if (state.isLoading) View.INVISIBLE else View.VISIBLE
        this.state = state
        state.providerDetails?.let {
            binding.providerItem = it
        }
        initializeViews(state)
//        activity?.invalidateOptionsMenu()
        renderFav()
        binding.ivFav.setOnClickListener {
            toggleFav()
        }
    }

    private fun initializeViews(state: MedicalProviderDetailsViewState) {
        val phoneNumbers = providerItem.phones?.replace(" ", "")?.replace("_x000D_\n", "-")?.replace("\r\n", "-")
                ?.replace('\n', '-')?.replace('\r', '-')?.split("-")

        val adapter = PhonesAdapter(dataBindingComponent, appExecutors)
        adapter.submitList(phoneNumbers)
        binding.rvPhones.adapter = adapter

        bMap.setOnClickListener {
            state.providerDetails?.address?.let { bMap.openMap(it, requireContext()) }
        }

        bClaiming.setOnClickListener {
            if (PreferencesHelper(requireContext()).isRegistered)
                navController().navigate(R.id.claimingFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.CLAIMING)
                navController().navigate(R.id.mobileFragment, bundle)
            }
        }

//        tvPhone.setOnClickListener {
//            openCallFragment(state.providerDetails?.phones!!)
//        }
    }

    //region
    fun toggleFav() {
        if (state.isFavorite)
            medicalProviderDetailsViewModel.removeFavorite(providerItem)
        else
            medicalProviderDetailsViewModel.addFavorite(providerItem)
    }

    fun renderFav() {
        binding.ivFav.setImageResource(if (state.isFavorite) R.drawable.ic_fav_white else R.drawable.ic_fav_outline)
    }

    fun openCallFragment(phones: String) {
        val fragmentManager = this@MedicalProviderDetailsFragment.fragmentManager
        val subSyndicatesFragment = PhonesFragment()
        val bundle = Bundle()
        bundle.putString("phones", phones)
        subSyndicatesFragment.arguments = bundle
        subSyndicatesFragment.setTargetFragment(this, 255)
        subSyndicatesFragment.show(requireFragmentManager(), "name")
    }
// endregion

    fun navController() = findNavController()
}
