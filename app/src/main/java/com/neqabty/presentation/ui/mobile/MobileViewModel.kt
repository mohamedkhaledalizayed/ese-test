package com.neqabty.presentation.ui.mobile

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.Login
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.util.PreferencesHelper
import javax.inject.Inject

class MobileViewModel @Inject constructor(val login: Login) : BaseViewModel() {

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MobileViewState> = MutableLiveData()

    init {
        viewState.value = MobileViewState()
    }

    fun registerUser(
            mobile: String,
            userNumber: String,
            token: String,
            prefs: PreferencesHelper
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(login.login(Login.PARAM_ACTION_UPGRADE, mobile, userNumber, token, prefs.token)
                .subscribe(
                        {
                            prefs.token = token
                            prefs.mobile = mobile
                            prefs.user = it.details!![0].userNumber!!
                            prefs.name = it.details!![0].name!!
                            prefs.isRegistered = true
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = true)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = false)
                            errorState.value = handleError(it)
                        }
                ))
    }
}
