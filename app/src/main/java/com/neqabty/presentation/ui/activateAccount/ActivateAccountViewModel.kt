package com.neqabty.presentation.ui.activateAccount

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.ActivateAccount
import com.neqabty.domain.usecases.SendSMS
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.UserEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivateAccountViewModel @Inject constructor(val sendSMS: SendSMS, val activateAccount: ActivateAccount) : BaseViewModel() {

    private val userEntityToUIMapper = UserEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ActivateAccountViewState> = MutableLiveData()

    init {
        viewState.value = ActivateAccountViewState()
    }


    fun sendSMS(
            mobile: String
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(sendSMS.sendSMS(mobile)
                .subscribe(
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = true)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = false)
                            errorState.value = handleError(it)
                        }
                ))
    }

    fun activateAccount(
            mobile: String,
            verificationCode: String,
            password: String
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(activateAccount.activateAccount(mobile, verificationCode, password)
                .map {
                    it.let {
                        userEntityToUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = true, user = it)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = false)
                            errorState.value = handleError(it)
                        }
                ))
    }
}
