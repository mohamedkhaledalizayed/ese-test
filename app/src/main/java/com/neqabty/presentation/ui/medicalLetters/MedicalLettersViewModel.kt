package com.neqabty.presentation.ui.medicalLetters

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalLetters
import com.neqabty.domain.usecases.GetMedicalRenewalData
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalLetterUI
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.mappers.MedicalLetterEntityUIMapper
import com.neqabty.presentation.mappers.MedicalRenewalEntityUIMapper
import javax.inject.Inject

class MedicalLettersViewModel @Inject constructor(private val getMedicalRenewalData: GetMedicalRenewalData, private val getMedicalLetters: GetMedicalLetters) : BaseViewModel() {

    private val medicalRenewalEntityUIMapper = MedicalRenewalEntityUIMapper()
    private val medicalLettersEntityUIMapper = MedicalLetterEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalLettersViewState> = MutableLiveData()

    init {
        viewState.value = MedicalLettersViewState(isLoading = true)
    }

    fun getMedicalRenewalData(mobileNumber: String, number: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(getMedicalRenewalData.getMedicalRenewalData(mobileNumber, number)
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
                isLoading = true,
                medicalRenewalUI = medicalRenewalData)
        viewState.value = newViewState
    }

    fun getMedicalLetters(benID: String, start: Int, end: Int, orderBy: String = "BenId", dir: String = "asc") {
        if(start == 0) viewState.value?.medicalLetterUI?.letters?.clear()
        addDisposable(getMedicalLetters.getMedicalLetters(benID, start, end, orderBy, dir)
                .flatMap {
                    it.let {
                        medicalLettersEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            if(!viewState.value?.medicalLetterUI?.letters.isNullOrEmpty()) {
                                viewState.value?.medicalLetterUI?.letters?.addAll(it.letters!!)
                                onMedicalLettersReceived(viewState.value?.medicalLetterUI!!)
                            }else
                                onMedicalLettersReceived(it)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onMedicalLettersReceived(medicalLetter: MedicalLetterUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                medicalLetterUI = medicalLetter)
        viewState.value = newViewState
    }
}
