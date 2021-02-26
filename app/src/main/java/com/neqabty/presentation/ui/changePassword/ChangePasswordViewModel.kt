package com.neqabty.presentation.ui.changePassword

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.ChangePassword
import com.neqabty.domain.usecases.SetNewPassword
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(val changePassword: ChangePassword, val setNewPassword: SetNewPassword) : BaseViewModel() {

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ChangePasswordViewState> = MutableLiveData()

    init {
        viewState.value = ChangePasswordViewState()
    }


    fun changePassword(mobile: String, currentPassword: String, newPassword: String) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(changePassword.changePassword(mobile, currentPassword, newPassword)
                .subscribe(
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = true, msg = it)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = false)
                            errorState.value = handleError(it)
                        }
                ))
    }

    fun setNewPassword(mobile: String, verificationCode: String, newPassword: String) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(setNewPassword.setNewPassword(mobile, verificationCode, newPassword)
                .subscribe(
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = true, msg = it)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = false)
                            errorState.value = handleError(it)
                        }
                ))
    }
}
