package com.neqabty.presentation.ui.medicalProceduresInquiryResult

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalBranchProcedures
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalBranchProcedureUI
import com.neqabty.presentation.mappers.MedicalBranchProceduresEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicalProceduresInquiryResultViewModel @Inject constructor(
    private val getMedicalBranchProcedures: GetMedicalBranchProcedures
) : BaseViewModel() {

    private val medicalBranchProceduresEntityUIMapper = MedicalBranchProceduresEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalProceduresInquiryResultViewState> = MutableLiveData()

    init {
        viewState.value = MedicalProceduresInquiryResultViewState()
    }

    fun getBranchProcedures(mobileNumber: String, procedureId: String, relationTypeId: String, areaId: String) {
        addDisposable(getMedicalBranchProcedures.getProcedures(mobileNumber, procedureId, relationTypeId, areaId)
                .flatMap {
                    it.let {
                        medicalBranchProceduresEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onBranchProceduresReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onBranchProceduresReceived(branchProcedures: List<MedicalBranchProcedureUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                branchProcedures = branchProcedures)
        viewState.value = newViewState
    }
}
