package com.neqabty.presentation.ui.medicalProceduresInquiry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalProceduresInquiryFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.MedicalProceduresInquiryLookupsUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicalProceduresInquiryFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalProceduresInquiryFragmentBinding>()

    private val medicalProceduresInquiryViewModel: MedicalProceduresInquiryViewModel by viewModels()

    var relationTypes: List<MedicalProceduresInquiryLookupsUI.RelationType>? = mutableListOf()
    var governsResultList: List<MedicalProceduresInquiryLookupsUI.Govern>? = mutableListOf()
    var areasResultList: List<MedicalProceduresInquiryLookupsUI.Area>? = mutableListOf()
    var categoriesList: List<MedicalProceduresInquiryLookupsUI.ProcedureCategory>? = mutableListOf()

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.medical_procedures_inquiry_fragment,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        medicalProceduresInquiryViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalProceduresInquiryViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                medicalProceduresInquiryViewModel.getLookups(sharedPref.mobile)
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })
        medicalProceduresInquiryViewModel.getLookups(sharedPref.mobile)
    }

    private fun initializeViews() {
        initializeSpinners()
    }

    private fun handleViewState(state: MedicalProceduresInquiryViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.llHolder.visibility = if (state.isLoading) View.GONE else View.VISIBLE

        state.relationTypes?.let {
            relationTypes = it
            governsResultList = state.governs
            areasResultList = state.areas
            categoriesList = state.categories
            initializeViews()
        }
    }

    //region

    fun initializeSpinners() {
        renderRelationTypes()
        renderGoverns()
        binding.bNext.setOnClickListener{
            navController().navigate(MedicalProceduresInquiryFragmentDirections.openMedicalProceduresInquiryCont(
                (binding.spRelationType.selectedItem as MedicalProceduresInquiryLookupsUI.RelationType).id,
                (binding.spArea.selectedItem as MedicalProceduresInquiryLookupsUI.Area).id,
                MedicalProceduresInquiryLookupsUI(categories = categoriesList)
            ))
        }
    }
    fun renderGoverns() {
        binding.spGovern.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, governsResultList!!)
        binding.spGovern.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                renderAreas()
            }
        }
        binding.spGovern.setSelection(0)
    }

    fun renderAreas() {
        var filteredAreasList: List<MedicalProceduresInquiryLookupsUI.Area>? = mutableListOf()

        filteredAreasList = areasResultList?.filter {
            it.govId == (binding.spGovern.selectedItem as MedicalProceduresInquiryLookupsUI.Govern).id
        }

        binding.spArea.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, filteredAreasList!!)
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            }
        }
        binding.spArea.setSelection(0)
    }

    fun renderRelationTypes() {
        binding.spRelationType.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, relationTypes!!)
        binding.spRelationType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
        binding.spRelationType.setSelection(0)
    }
// endregion

    fun navController() = findNavController()
}
