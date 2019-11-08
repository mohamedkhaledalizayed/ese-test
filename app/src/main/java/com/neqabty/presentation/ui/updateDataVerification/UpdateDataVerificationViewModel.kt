package com.neqabty.presentation.ui.updateDataVerification

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.UpdateUserData
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.UpdateUserDataUI
import com.neqabty.presentation.entities.VerifyUserDataUI
import com.neqabty.presentation.mappers.UpdateUserDataEntityUIMapper
import com.neqabty.presentation.mappers.VerifyUserDataEntityUIMapper

import javax.inject.Inject

class UpdateDataVerificationViewModel @Inject constructor(private val updateUserData: UpdateUserData) : BaseViewModel() {

    private val UpdateUserDataEntityUIMapper = UpdateUserDataEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<UpdateDataVerificationViewState> = MutableLiveData()

    init {
        viewState.value = UpdateDataVerificationViewState()
    }

    fun updateUserData(userNumber: String,fullName: String,nationalID: String,gender: String,userID: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(updateUserData.updateUserData(userNumber,fullName, nationalID, gender, userID)
                .map {
                    it.let {
                        UpdateUserDataEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onDataReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }

    private fun onDataReceived(updateUserDataUI: UpdateUserDataUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                message = updateUserDataUI.message)
        viewState.value = newViewState
    }
}
