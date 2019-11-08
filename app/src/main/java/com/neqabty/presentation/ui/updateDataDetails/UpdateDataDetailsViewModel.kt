package com.neqabty.presentation.ui.updateDataDetails

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetUpdateUserDataInquiry
import com.neqabty.domain.usecases.VerifyUpdateUserData
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.InquireUpdateUserDataUI
import com.neqabty.presentation.entities.VerifyUserDataUI
import com.neqabty.presentation.mappers.InquireUpdateUserDataEntityUIMapper
import com.neqabty.presentation.mappers.VerifyUserDataEntityUIMapper

import javax.inject.Inject

class UpdateDataDetailsViewModel @Inject constructor(private val verifyUpdateUserData: VerifyUpdateUserData) : BaseViewModel() {

    private val verifyUserDataEntityUIMapper = VerifyUserDataEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<UpdateDataDetailsViewState> = MutableLiveData()

    init {
        viewState.value = UpdateDataDetailsViewState()
    }

    fun verifyUser(userNumber: String, mobileNumber: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(verifyUpdateUserData.verifyUser(userNumber,mobileNumber)
                .map {
                    it.let {
                        verifyUserDataEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onVerifyUserDataReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }

    private fun onVerifyUserDataReceived(verifyUserData: VerifyUserDataUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                code = verifyUserData.code)
        viewState.value = newViewState
    }
}
