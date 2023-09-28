package com.neqabty.presentation.ui.medicalProceduresInquiryCont

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
import com.neqabty.databinding.MedicalProceduresInquiryContFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.MedicalProcedureUI
import com.neqabty.presentation.entities.MedicalProceduresInquiryLookupsUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicalProceduresInquiryContFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalProceduresInquiryContFragmentBinding>()

    private val medicalProceduresInquiryViewModel: MedicalProceduresInquiryContViewModel by viewModels()

    var categories: List<MedicalProceduresInquiryLookupsUI.ProcedureCategory>? = mutableListOf()
    var subcategories: List<MedicalProceduresInquiryLookupsUI.ProcedureCategory>? = mutableListOf()
    var procedures: List<MedicalProcedureUI>? = mutableListOf()

    lateinit var params: MedicalProceduresInquiryContFragmentArgs
    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.medical_procedures_inquiry_cont_fragment,
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
                medicalProceduresInquiryViewModel.getProcedures(sharedPref.mobile, (binding.spProcedureSubCat.selectedItem as MedicalProceduresInquiryLookupsUI.ProcedureCategory).id.toString())
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })

        params = MedicalProceduresInquiryContFragmentArgs.fromBundle(requireArguments())
        categories = params.data.categories

        initializeViews()
    }

    private fun initializeViews() {
        renderCategories()
        binding.bNext.setOnClickListener{
            navController().navigate(MedicalProceduresInquiryContFragmentDirections.openMedicalProceduresInquiryResult(
                (binding.spProcedures.selectedItem as MedicalProcedureUI).id, params.relationTypeId, params.areaID)
            )
        }
    }

    private fun handleViewState(state: MedicalProceduresInquiryContViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.procedures?.let {
            procedures = it
            renderProcedures()
        }
    }

    //region
    fun renderCategories() {
        var filteredCategoriesList: List<MedicalProceduresInquiryLookupsUI.ProcedureCategory>? = mutableListOf()

        filteredCategoriesList = categories?.filter {
            it.parentCategoryId == -1
        }
        binding.spProcedureCat.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, filteredCategoriesList!!)
        binding.spProcedureCat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                renderSubcategories()
            }
        }
        binding.spProcedureCat.setSelection(0)
    }

    fun renderSubcategories() {
        var filteredSubcategoriesList: List<MedicalProceduresInquiryLookupsUI.ProcedureCategory>? = mutableListOf()

        filteredSubcategoriesList = categories?.filter {
            it.parentCategoryId == (binding.spProcedureCat.selectedItem as MedicalProceduresInquiryLookupsUI.ProcedureCategory).id
        }

        if(filteredSubcategoriesList!!.isEmpty()) {
            binding.tvProcedureSubCat.visibility = View.GONE
            binding.spProcedureSubCat.visibility = View.GONE
            binding.tvProcedure.visibility = View.GONE
            binding.spProcedures.visibility = View.GONE
        }
        else {
            binding.tvProcedureSubCat.visibility = View.VISIBLE
            binding.spProcedureSubCat.visibility = View.VISIBLE
            binding.tvProcedure.visibility = View.VISIBLE
            binding.spProcedures.visibility = View.VISIBLE
        }

        binding.spProcedureSubCat.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, filteredSubcategoriesList!!)
        binding.spProcedureSubCat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                medicalProceduresInquiryViewModel.getProcedures(sharedPref.mobile, (filteredSubcategoriesList?.get(position) as MedicalProceduresInquiryLookupsUI.ProcedureCategory).id.toString())
            }
        }
        binding.spProcedureSubCat.setSelection(0)
    }

    fun renderProcedures() {
        binding.spProcedures.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, procedures!!)
        binding.spProcedures.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
        binding.spProcedures.setSelection(0)
    }
// endregion

    fun navController() = findNavController()
}
