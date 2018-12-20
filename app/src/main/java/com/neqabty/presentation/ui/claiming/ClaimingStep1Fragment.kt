package com.neqabty.presentation.ui.claiming

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
import android.widget.Toast
import com.neqabty.R
import com.neqabty.databinding.Claiming1FragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.DoctorUI
import com.neqabty.presentation.entities.SpecializationUI
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class ClaimingStep1Fragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<Claiming1FragmentBinding>()

    lateinit var claimingViewModel: ClaimingViewModel

    var doctorsResultList: List<DoctorUI>? = mutableListOf()
    var areasResultList: List<AreaUI>? = mutableListOf()
    var specializationsResultList: List<SpecializationUI>? = mutableListOf()
    var specializationID: String = "1"
    var areaID: String = "1"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.claiming1_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
        initializeViews()
        claimingViewModel.getAllContent()
    }

    private fun handleViewState(state: ClaimingViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.doctors?.let {
            doctorsResultList = it
        }
        state.areas?.let {
            areasResultList = it
        }
        state.specializations?.let {
            specializationsResultList = it
        }
        if (state.doctors != null && state.specializations != null && state.areas != null)
            initializeSpinners()
    }

    fun initializeViews() {
    }

    fun initializeSpinners() {
        renderAreas()
        renderSpecializations()
    }

    //region
    fun renderDoctors() {
        var filteredDoctorsList: List<DoctorUI>? = mutableListOf()

        filteredDoctorsList = doctorsResultList?.filter {
            it.degreeCode.equals(specializationID) && it.areaCode.equals(areaID)
        }

        binding.spDoctorName.adapter = ArrayAdapter<DoctorUI>(requireContext(), R.layout.spinner_item, filteredDoctorsList)
        binding.spDoctorName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                val item = parent.getItemAtPosition(position) as DoctorUI
//                        Toast.makeText(requireContext(), item.phoneNumber, LENGTH_LONG).show()

            }
        }
    }

    fun renderAreas() {
        binding.spArea.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, areasResultList)
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                areaID = (parent.getItemAtPosition(position) as AreaUI).code!!
                renderDoctors()
            }
        }
    }

    fun renderSpecializations() {
        binding.spSpecialization.adapter = ArrayAdapter<SpecializationUI>(requireContext(), R.layout.spinner_item, specializationsResultList)
        binding.spSpecialization.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                specializationID = (parent.getItemAtPosition(position) as SpecializationUI).code!!
                renderDoctors()
            }
        }
    }

// endregion

}
