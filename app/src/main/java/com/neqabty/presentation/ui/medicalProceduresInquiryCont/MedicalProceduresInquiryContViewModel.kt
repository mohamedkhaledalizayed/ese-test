package com.neqabty.presentation.ui.medicalProceduresInquiryCont

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalProceduresByCategory
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalProcedureUI
import com.neqabty.presentation.mappers.MedicalProceduresEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicalProceduresInquiryContViewModel @Inject constructor(
    private val getMedicalProceduresByCategory: GetMedicalProceduresByCategory
) : BaseViewModel() {

    private val medicalProceduresEntityUIMapper = MedicalProceduresEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalProceduresInquiryContViewState> = MutableLiveData()

    init {
        viewState.value = MedicalProceduresInquiryContViewState()
    }

    fun getProcedures(mobile: String, catId: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(getMedicalProceduresByCategory.getProcedures(mobile, catId)
                .flatMap {
                    it.let {
                        medicalProceduresEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onProceduresReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onProceduresReceived(procedures: List<MedicalProcedureUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                procedures = procedures)
        viewState.value = newViewState
    }
}
