package com.neqabty.presentation.ui.inquireMedicalLetters

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalLetterByID
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MedicalLetterUI
import com.neqabty.presentation.mappers.MedicalLetterItemEntityUIMapper
import javax.inject.Inject

class InquireMedicalLettersViewModel @Inject constructor(private val getMedicalLetterByID: GetMedicalLetterByID) : BaseViewModel() {

    private val medicalLetterItemEntityUIMapper = MedicalLetterItemEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<InquireMedicalLettersViewState> = MutableLiveData()

//    init {
//        viewState.value = InquireMedicalLettersViewState(isLoading = false)
//    }

    fun getMedicalLetterByID(mobileNumber: String, userNumber: String, id: String) {
        addDisposable(getMedicalLetterByID.getMedicalLetterByID(mobileNumber, userNumber, id)
                .flatMap {
                    it.let {
                        medicalLetterItemEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            onMedicalLetterReceived(it)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onMedicalLetterReceived(medicalLetterItem: MedicalLetterUI.LetterItem) {
        val newViewState = InquireMedicalLettersViewState(
                isLoading = false,
                medicalLetterItemUI = medicalLetterItem)
        viewState.value = newViewState
    }
}
