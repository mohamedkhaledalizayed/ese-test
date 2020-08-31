package com.neqabty.presentation.ui.chooseArea

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.ChooseAreaFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.GovernUI
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject

class ChooseAreaFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<ChooseAreaFragmentBinding>()

    @Inject
    lateinit var chooseAreaViewModel: ChooseAreaViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    var governsResultList: List<GovernUI>? = mutableListOf()
    var areasResultList: List<AreaUI>? = mutableListOf()
    var governID: Int = 0
    var areaID: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.choose_area_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        chooseAreaViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(chooseAreaViewModel::class.java)

        chooseAreaViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        chooseAreaViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                chooseAreaViewModel.getAllContent1()
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            })
        })
        chooseAreaViewModel.getAllContent1()
        initializeViews()
    }

    private fun initializeViews() {
        binding.bNext.setOnClickListener {
//                ClaimingData.areaId = (spArea.selectedItem as AreaUI).id
//                ClaimingData.governId = (spGovern.selectedItem as GovernUI).id
            navController().navigate(
                    ChooseAreaFragmentDirections.openMedical(governID, areaID)
            )
        }
    }

    private fun handleViewState(state: ChooseAreaViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.governs != null && state.areas != null) {
            binding.llHolder.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            state.governs?.let {
                governsResultList = it
            }
            state.areas?.let {
                areasResultList = it
            }
            if (state.governs != null && state.areas != null)
                initializeSpinners()
        }
    }

    fun initializeSpinners() {
        renderGoverns()
    }

    //region
    fun renderGoverns() {
        binding.spGovern.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, governsResultList!!)
        binding.spGovern.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                governID = (parent.getItemAtPosition(position) as GovernUI).id
                renderAreas()
            }
        }
        binding.spGovern.setSelection(0)
    }

    fun renderAreas() {
        var filteredAreasList: List<AreaUI>? = mutableListOf()

        filteredAreasList = areasResultList?.filter {
            it.govId == governID
        }

        binding.spArea.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, filteredAreasList!!)
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                areaID = (parent.getItemAtPosition(position) as AreaUI).id
            }
        }
        binding.spArea.setSelection(0)
    }

// endregion

    fun navController() = findNavController()
}
