package com.neqabty.presentation.ui.medicalProceduresInquiryResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalProceduresResultFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicalProceduresInquiryResultFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalProceduresResultFragmentBinding>()

    private val medicalProceduresInquiryViewModel: MedicalProceduresInquiryResultViewModel by viewModels()

    lateinit var params: MedicalProceduresInquiryResultFragmentArgs

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.medical_procedures_result_fragment,
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
                medicalProceduresInquiryViewModel.getBranchProcedures(sharedPref.mobile, params.procedureId.toString(), params.relationTypeId.toString(), params.areaID.toString())
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })
        params = MedicalProceduresInquiryResultFragmentArgs.fromBundle(requireArguments())
        medicalProceduresInquiryViewModel.getBranchProcedures(sharedPref.mobile, params.procedureId.toString(), params.relationTypeId.toString(), params.areaID.toString())
    }

    private fun handleViewState(state: MedicalProceduresInquiryResultViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.branchProcedures?.let {
            val adapter = MedicalInquiryBranchAdapter(dataBindingComponent, appExecutors) { }
            binding.rvProcedures.adapter = adapter
            adapter.submitList(it)
        }
    }

    //region
// endregion

    fun navController() = findNavController()
}
