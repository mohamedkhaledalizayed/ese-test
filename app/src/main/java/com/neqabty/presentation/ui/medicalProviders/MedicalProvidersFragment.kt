package com.neqabty.presentation.ui.medicalProviders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalProvidersFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.SpecializationUI
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.medical_providers_fragment.*
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
    var specializations = mutableListOf<SpecializationUI>()
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
//        if (title.isNotBlank()) {
//            setToolbarTitle(title)
//        }

        val adapter = MedicalProvidersAdapter(dataBindingComponent, appExecutors) { provider ->
            provider.typeID = categoryId.toString()
            provider.typeName = title
            navController().navigate(
                    MedicalProvidersFragmentDirections.openProviderDetails(provider)
            )
        }
        this.adapter = adapter
        binding.rvProviders.adapter = adapter

        medicalProvidersViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalProvidersViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE

                if (categoryId == 2)
                    medicalProvidersViewModel.getMedicalProfessions(categoryId.toString(), governID.toString(), areaID.toString())
                else
                    medicalProvidersViewModel.getMedicalProviders(categoryId.toString(), governID.toString(), areaID.toString(), "", degreeID)

            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        if (categoryId == 2)
            medicalProvidersViewModel.getMedicalProfessions(categoryId.toString(), governID.toString(), areaID.toString())
        else
            medicalProvidersViewModel.getMedicalProviders(categoryId.toString(), governID.toString(), areaID.toString(), "", degreeID)
    }

    private fun handleViewState(state: MedicalProvidersViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.providers?.let {
            if (categoryId == 2)
                spSpecialization.visibility = View.VISIBLE
            else
                spSpecialization.visibility = View.GONE

            if (it.size == 0)
                binding.tvNoDataFound.visibility = View.VISIBLE
            else
                binding.tvNoDataFound.visibility = View.GONE

            adapter.submitList(it)
            state.providers = null
            return
        }
        state.professions?.let {
            specializations = it.toMutableList()
            state.professions = null
            spSpecialization.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, specializations)
            initializeViews()
        }
    }

    fun initializeViews() {
        spSpecialization.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                medicalProvidersViewModel.getMedicalProviders(categoryId.toString(), governID.toString(), areaID.toString(), (parent.getItemAtPosition(position) as SpecializationUI).id.toString(), degreeID)
            }
        }
        spSpecialization.setSelection(0)
        medicalProvidersViewModel.getMedicalProviders(categoryId.toString(), governID.toString(), areaID.toString(), specializations[0].id.toString(), degreeID)
    }

//region

// endregion

    fun navController() = findNavController()
}
