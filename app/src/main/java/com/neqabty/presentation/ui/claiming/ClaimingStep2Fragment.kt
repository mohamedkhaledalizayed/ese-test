package com.neqabty.presentation.ui.claiming

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.neqabty.R
import com.neqabty.databinding.Claiming2FragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.DegreeUI
import com.neqabty.presentation.entities.DoctorUI
import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class ClaimingStep2Fragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<Claiming2FragmentBinding>()

    lateinit var claimingViewModel: ClaimingViewModel
    var areasResultList: List<AreaUI>? = mutableListOf()
    var providersTypesResultList: List<DegreeUI>? = mutableListOf()
    var providersResultList: List<ProviderUI>? = mutableListOf()

    var providerTypeID: String = "1"
    var areaID: String = "1"


    lateinit var pager: ViewPager
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.claiming2_fragment,
                container,
                false,
                dataBindingComponent
        )
        pager = container as ViewPager
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser)
            initializeViews()
    }

    fun initializeViews() {
        claimingViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ClaimingViewModel::class.java)

        claimingViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        claimingViewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
            }
        })

        binding.edNumber.setText(ClaimingData.number)
        binding.edDoctor.setText(ClaimingData.doctorName)

        binding.bNext.setOnClickListener({
            pager.setCurrentItem(2,true)
        })

        claimingViewModel.getAllContent2("1")
    }


    private fun handleViewState(state: ClaimingViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.areas?.let {
            areasResultList = it
        }
        state.providers?.let {
            providersResultList = it
        }
        if (state.providers != null && state.areas != null)
            initializeSpinners()
    }

//region

    fun initializeSpinners() {
        renderAreas()
//        renderProvidersTypes()
        renderProviders()
    }

    fun renderAreas() {
        binding.spArea.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, areasResultList)
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                areaID = (parent.getItemAtPosition(position) as AreaUI).id.toString()!!
                renderProviders()
            }
        }
    }

    fun renderProviders() {
        var filteredProvidersList: List<ProviderUI>? = mutableListOf()

        filteredProvidersList = providersResultList?.filter {
            it.areaId.equals(areaID)
        }

        binding.spProvider.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, filteredProvidersList)
        binding.spProvider.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
    }


// endregion

}
