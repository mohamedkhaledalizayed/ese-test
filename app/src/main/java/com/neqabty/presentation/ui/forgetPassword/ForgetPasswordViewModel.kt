package com.neqabty.presentation.ui.forgetPassword

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.ForgetPassword
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(val forgetPassword: ForgetPassword) : BaseViewModel() {

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ForgetPasswordViewState> = MutableLiveData()

    init {
        viewState.value = ForgetPasswordViewState()
    }


    fun forgetPassword(mobile: String, userNumber: String) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(forgetPassword.forgetPassword(mobile, userNumber)
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
