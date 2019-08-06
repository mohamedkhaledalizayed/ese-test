package com.neqabty.presentation.ui.medicalProviderDetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
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
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.util.HasFavoriteOptionsMenu
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject

class MedicalProviderDetailsFragment : BaseFragment(), Injectable, HasFavoriteOptionsMenu {
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

        val params = MedicalProviderDetailsFragmentArgs.fromBundle(arguments!!)
        providerItem = params.medicalProviderItem
        medicalProviderDetailsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })

        medicalProviderDetailsViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                medicalProviderDetailsViewModel.isFavorite(providerItem)
                medicalProviderDetailsViewModel.getProviderDetails(providerItem.id.toString(), providerItem.type!!)
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        medicalProviderDetailsViewModel.isFavorite(providerItem)
        medicalProviderDetailsViewModel.getProviderDetails(providerItem.id.toString(), providerItem.type!!)
    }

    private fun handleViewState(state: MedicalProviderDetailsViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.llHolder.visibility = if (state.isLoading) View.GONE else View.VISIBLE
        this.state = state
        state.providerDetails?.let {
            binding.providerItem = it
        }
        activity?.invalidateOptionsMenu()
    }

    //region
    override fun toggleFav() {
        if (state.isFavorite)
            medicalProviderDetailsViewModel.removeFavorite(providerItem)
        else
            medicalProviderDetailsViewModel.addFavorite(providerItem)
    }

    override fun renderFav(): Int {
         return if (state.isFavorite) R.mipmap.added_fav else R.mipmap.add_fav
    }
// endregion

    fun navController() = findNavController()
}
