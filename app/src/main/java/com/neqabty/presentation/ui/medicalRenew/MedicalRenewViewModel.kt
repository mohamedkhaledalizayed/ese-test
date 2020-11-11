package com.neqabty.presentation.ui.medicalRenew

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalRenewalData
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.mappers.MedicalRenewalEntityUIMapper
import javax.inject.Inject

class MedicalRenewViewModel @Inject constructor(
        private val getMedicalRenewalData: GetMedicalRenewalData
) : BaseViewModel() {

    private val medicalRenewalEntityUIMapper = MedicalRenewalEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalRenewViewState> = MutableLiveData()

    init {
        viewState.value = MedicalRenewViewState()
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
