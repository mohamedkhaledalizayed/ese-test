package com.neqabty.presentation.ui.medicalRenewUpdate

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalRenewalData
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.mappers.MedicalRenewalEntityUIMapper
import javax.inject.Inject

class MedicalRenewUpdateViewModel @Inject constructor(
        private val getMedicalRenewalData: GetMedicalRenewalData
) : BaseViewModel() {

    private val medicalRenewalEntityUIMapper = MedicalRenewalEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalRenewUpdateViewState> = MutableLiveData()

    init {
        viewState.value = MedicalRenewUpdateViewState()
    }


    fun getMedicalRenewalData(number: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        viewState.value?.medicalRenewalUI?.let {
            onMedicalRenewalDataReceived(it)
        } ?: addDisposable(getMedicalRenewalData.getMedicalRenewalData(number)
                .map {
                    it.let {
                        medicalRenewalEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onMedicalRenewalDataReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }


    private fun onMedicalRenewalDataReceived(medicalRenewalData: MedicalRenewalUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                medicalRenewalUI = medicalRenewalData)
        viewState.value = newViewState
    }

}
