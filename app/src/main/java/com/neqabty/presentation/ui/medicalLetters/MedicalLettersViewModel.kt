package com.neqabty.presentation.ui.medicalLetters

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetLiteFollowersListData
import com.neqabty.domain.usecases.GetMedicalLetters
import com.neqabty.domain.usecases.ValidateUserForClaiming
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.ClaimingValidationUI
import com.neqabty.presentation.entities.LiteFollowersListUI
import com.neqabty.presentation.entities.MedicalLetterUI
import com.neqabty.presentation.mappers.ClaimingValidationEntityUIMapper
import com.neqabty.presentation.mappers.LiteFollowersListEntityUIMapper
import com.neqabty.presentation.mappers.MedicalLetterEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicalLettersViewModel @Inject constructor(private val validateUserForClaiming: ValidateUserForClaiming,
                                                  private val getLiteFollowersListData: GetLiteFollowersListData, private val getMedicalLetters: GetMedicalLetters) : BaseViewModel() {

    private val claimingValidationEntityUIMapper = ClaimingValidationEntityUIMapper()
    private val liteFollowersListEntityUIMapper = LiteFollowersListEntityUIMapper()
    private val medicalLettersEntityUIMapper = MedicalLetterEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalLettersViewState> = MutableLiveData()

    init {
        viewState.value = MedicalLettersViewState(isLoading = true)
    }


    fun validateUser(number: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(validateUserForClaiming.validateUser(number)
                .map {
                    it.let {
                        claimingValidationEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onValidationReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onValidationReceived(member: ClaimingValidationUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                member = member)
        viewState.value = newViewState
    }


    fun getMedicalRenewalData(mobileNumber: String, number: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(getLiteFollowersListData.getLiteFollowersListData(mobileNumber, number)
                .flatMap {
                    it.let {
                        liteFollowersListEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            onLiteFollowersListDataReceived(it)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }


    private fun onLiteFollowersListDataReceived(followersList: List<LiteFollowersListUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = true,
                liteFollowersListUI = followersList)
        viewState.value = newViewState
    }

    fun getMedicalLetters(benID: String, start: Int, end: Int, orderBy: String = "BenId", dir: String = "asc", mobileNumber: String) {
        if(start == 0) viewState.value?.medicalLetterUI?.letters?.clear()
        addDisposable(getMedicalLetters.getMedicalLetters(benID, start, end, orderBy, dir, mobileNumber)
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
