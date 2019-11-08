package com.neqabty.presentation.ui.medicalProviders

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
import com.neqabty.databinding.MedicalProvidersFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject

class MedicalProvidersFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<MedicalProvidersFragmentBinding>()
    private var adapter by autoCleared<MedicalProvidersAdapter>()
    lateinit var medicalProvidersViewModel: MedicalProvidersViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    var areaID: Int = 0
    var governID: Int = 0
    var degreeID: String? = ""
    var professionID: String? = ""

    var categoryId: Int = 0
    var title: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_providers_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        medicalProvidersViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MedicalProvidersViewModel::class.java)

        val params = MedicalProvidersFragmentArgs.fromBundle(arguments!!)
        title = params.title
        categoryId = params.categoryId
        areaID = params.areaID
        governID = params.governID
        professionID = params.professionID
        degreeID = params.degreeID
        if (title.isNotBlank()) { setToolbarTitle(title) }

        val adapter = MedicalProvidersAdapter(dataBindingComponent, appExecutors) { provider ->
            provider.type = categoryId.toString()
            navController().navigate(
                    MedicalProvidersFragmentDirections.openProviderDetails(provider)
            )
        }
        this.adapter = adapter
        binding.rvProviders.adapter = adapter

        medicalProvidersViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalProvidersViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                medicalProvidersViewModel.getMedicalProviders(categoryId.toString(), governID.toString(), areaID.toString(), professionID, degreeID)
            }, cancelCallback = {
                navController().navigateUp()
            })
        })

        medicalProvidersViewModel.getMedicalProviders(categoryId.toString(), governID.toString(), areaID.toString(), professionID, degreeID)
    }

    private fun handleViewState(state: MedicalProvidersViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.providers?.let {
            if(it.size == 0)
                binding.tvNoDataFound.visibility = View.VISIBLE
            adapter.submitList(it)
        }
    }
    fun initializeViews() {
    }

//region

// endregion

    fun navController() = findNavController()
}
