package com.neqabty.presentation.ui.medicalProceduresInquiry

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalProceduresInquiryLookups
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalProceduresInquiryLookupsUI
import com.neqabty.presentation.mappers.MedicalProceduresInquiryLookupsEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicalProceduresInquiryViewModel @Inject constructor(
    private val getMedicalProceduresInquiryLookups: GetMedicalProceduresInquiryLookups
) : BaseViewModel() {

    private val medicalProceduresInquiryLookupsEntityUIMapper = MedicalProceduresInquiryLookupsEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalProceduresInquiryViewState> = MutableLiveData()

    init {
        viewState.value = MedicalProceduresInquiryViewState()
    }

    fun getLookups(mobile: String) {
        addDisposable(getMedicalProceduresInquiryLookups.getLookups(mobile)
                .map {
                    it.let {
                        medicalProceduresInquiryLookupsEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onLookupsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onLookupsReceived(lookupsData: MedicalProceduresInquiryLookupsUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                governs = lookupsData.governs,
                areas = lookupsData.areas,
                categories = lookupsData.categories,
                relationTypes = lookupsData.relationTypes)
        viewState.value = newViewState
    }
}
