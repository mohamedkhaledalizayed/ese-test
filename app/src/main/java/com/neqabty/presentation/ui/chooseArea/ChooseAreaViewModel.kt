package com.neqabty.presentation.ui.chooseArea

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalDirectoryLookups
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.MedicalDirectoryLookupsEntityUIMapper
import javax.inject.Inject

class ChooseAreaViewModel @Inject constructor(val getMedicalDirectoryLookups: GetMedicalDirectoryLookups) : BaseViewModel() {
    private val medicalDirectoryLookupsEntityUIMapper = MedicalDirectoryLookupsEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ChooseAreaViewState> = MutableLiveData()

    init {
        viewState.value = ChooseAreaViewState(isLoading = true)
    }

    fun getAllContent1(mobileNumber: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        val governsDisposable = getMedicalDirectoryLookups.getLookups(mobileNumber)
                .map {
                    it.let {
                        medicalDirectoryLookupsEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(governs = it.governs, areas = it.areas)
                            onContent1Received()
                        },
                        { errorState.value = handleError(it) }
                )

        viewState.value?.governs?.let {
            onContent1Received()
        } ?: addDisposable(governsDisposable)
    }

    private fun onContent1Received() {
        if (viewState.value?.governs != null && viewState.value?.areas != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
