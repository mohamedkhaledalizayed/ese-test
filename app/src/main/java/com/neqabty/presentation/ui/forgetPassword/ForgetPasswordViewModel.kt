package com.neqabty.presentation.ui.forgetPassword

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.ForgetPassword
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(val forgetPassword: ForgetPassword) : BaseViewModel() {

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ForgetPasswordViewState> = MutableLiveData()

    init {
        viewState.value = ForgetPasswordViewState()
    }


    fun forgetPassword(mobile: String, userNumber: String, natId: String) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(forgetPassword.forgetPassword(mobile, userNumber, natId)
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
