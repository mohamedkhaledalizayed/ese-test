package com.neqabty.presentation.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.SearchFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.*
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject


class SearchFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<SearchFragmentBinding>()

    @Inject
    lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    var governsResultList: List<GovernUI>? = mutableListOf()
    var areasResultList: List<AreaUI>? = mutableListOf()
    var providersTypesResultList: List<ProviderTypeUI>? = mutableListOf()
    var specializationsResultList: List<SpecializationUI>? = mutableListOf()
    var degreesResultList: List<DegreeUI>? = mutableListOf()

    var governID: Int = 0
    var areaID: Int = 0
    var providerTypeID: Int = 0
    var specializationID: Int = 0
    var degreeID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.search_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SearchViewModel::class.java)

        searchViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        searchViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                searchViewModel.getAllContent()
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            })
        })
        searchViewModel.getAllContent()
        initializeViews()
    }

    private fun initializeViews() {
        binding.bNext.setOnClickListener {
            navController().navigate(
                    SearchFragmentDirections.openProviders("", providerTypeID, governID, areaID, specializationID.toString(), if (degreeID == 0) "" else degreeID.toString())
            )
        }
    }

    private fun handleViewState(state: SearchViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.governs != null && state.areas != null && state.providerTypes != null && state.degrees != null && state.professions != null) {
            binding.svContent.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            state.governs?.let {
                governsResultList = it
            }
            state.areas?.let {
                areasResultList = it
            }
            state.providerTypes?.let {
                providersTypesResultList = it
            }
            state.professions?.let {
                specializationsResultList = it
            }
            state.degrees?.let {
                degreesResultList = it
            }
                initializeSpinners()
        }
    }

    fun initializeSpinners() {
        renderGoverns()
        renderProvidersTypes()
    }

    //region
    fun renderGoverns() {
        binding.spGovern.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, governsResultList)
        binding.spGovern.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                searchViewModel.govIndex = position
                governID = (parent.getItemAtPosition(position) as GovernUI).id
                renderAreas()
            }
        }
        binding.spGovern.setSelection(searchViewModel.govIndex)
    }

    fun renderAreas() {
        var filteredAreasList: List<AreaUI>? = mutableListOf()

        filteredAreasList = areasResultList?.filter {
            it.govId == governID
        }

        binding.spArea.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, filteredAreasList)
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                searchViewModel.areaIndex = position
                areaID = (parent.getItemAtPosition(position) as AreaUI).id
            }
        }
        binding.spArea.setSelection(searchViewModel.areaIndex)
    }

    fun renderProvidersTypes() {
        binding.spProviderType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, providersTypesResultList)
        binding.spProviderType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                if (providerTypeID.equals((parent.getItemAtPosition(position) as ProviderTypeUI).id.toString()!!))
//                    return
                searchViewModel.providerTypeIndex = position
                providerTypeID = (parent.getItemAtPosition(position) as ProviderTypeUI).id
                if (providerTypeID == 2) {
                    binding.llDoctors.visibility = View.VISIBLE
                    renderDegrees()
                    renderSpecializations()
                } else {
                    binding.llDoctors.visibility = View.GONE
                }
            }
        }
        binding.spProviderType.setSelection(searchViewModel.providerTypeIndex)
    }

    fun renderDegrees() {
        binding.spDegree.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, degreesResultList)
        binding.spDegree.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                searchViewModel.degreeIndex = position
                degreeID = (parent.getItemAtPosition(position) as DegreeUI).id
            }
        }
        binding.spDegree.setSelection(searchViewModel.degreeIndex)
    }

    fun renderSpecializations() {
        binding.spSpecialization.adapter = ArrayAdapter<SpecializationUI>(requireContext(), R.layout.spinner_item, specializationsResultList)
        binding.spSpecialization.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                searchViewModel.professionIndex = position
                specializationID = (parent.getItemAtPosition(position) as SpecializationUI).id
            }
        }
        binding.spSpecialization.setSelection(searchViewModel.professionIndex)
    }

// endregion

    fun navController() = findNavController()
}
