package com.neqabty.presentation.ui.medicalRenewUpdate

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.entities.MedicalRenewalEntity
import com.neqabty.domain.usecases.GetMedicalRenewalData
import com.neqabty.domain.usecases.UpdateMedicalRenewalData
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.entities.MedicalRenewalUpdateUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.mappers.MedicalRenewalEntityUIMapper
import com.neqabty.presentation.mappers.MedicalRenewalUIEntityMapper
import com.neqabty.presentation.mappers.MedicalRenewalUpdateEntityUIMapper
import javax.inject.Inject

class MedicalRenewUpdateViewModel @Inject constructor(
        private val getMedicalRenewalData: GetMedicalRenewalData,
        private val updateMedicalRenewalData: UpdateMedicalRenewalData
) : BaseViewModel() {

    private val medicalRenewalEntityUIMapper = MedicalRenewalEntityUIMapper()
    private val medicalRenewalUpdateEntityUIMapper = MedicalRenewalUpdateEntityUIMapper()
    private val medicalRenewalUIEntityMapper = MedicalRenewalUIEntityMapper()
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


    fun updateMedicalRenewalData(medicalRenewalUI: MedicalRenewalUI) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(updateMedicalRenewalData.updateMedicalRenewalData(medicalRenewalUIEntityMapper.mapFrom(medicalRenewalUI))
                .map {
                    it.let {
                        medicalRenewalUpdateEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onMedicalRenewalUpdateDataReceived(it) },
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


    private fun onMedicalRenewalUpdateDataReceived(medicalRenewalUpdateUI: MedicalRenewalUpdateUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                medicalRenewalUpdateUI = medicalRenewalUpdateUI)
        viewState.value = newViewState
    }

}
