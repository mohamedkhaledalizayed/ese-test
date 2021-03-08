package com.neqabty

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAppVersion
import com.neqabty.domain.usecases.Login
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.UserUI
import com.neqabty.presentation.mappers.UserEntityUIMapper
import com.neqabty.presentation.util.PreferencesHelper
import javax.inject.Inject

class MainViewModel @Inject constructor(val login: Login,
                                        private val getAppVersion: GetAppVersion) : BaseViewModel() {
    private val userEntityToUIMapper = UserEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        viewState.value = MainViewState()
    }

    fun login(
            mobile: String,
            userNumber: String,
            token: String,
            prefs: PreferencesHelper
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(login.login(Login.PARAM_ACTION_UPDATE_TOKEN, mobile, userNumber, token, prefs.token,"")
                .map {
                    it.let {
                        userEntityToUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            prefs.token = token
                            onUserReceived(it)
                        },
                        { errorState.value = handleError(it) }
                )
        )
    }

    fun getAppVersion() {
        viewState.value = viewState.value?.copy(isLoading = true)
        viewState.value?.appVersion?.let {
            onVersionReceived()
        } ?: addDisposable(getAppVersion.observable()
                .subscribe(
                        {
                            viewState.value = viewState.value?.copy(appVersion = it.appVersion.toInt())
                            onVersionReceived()
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }
    private fun onUserReceived(user: UserUI) {

        val newViewState = viewState.value?.copy(
                isLoading = false,
                user = user)

        viewState.value = newViewState
    }


    private fun onVersionReceived() {
        if (viewState.value?.appVersion != null)// && viewState.value?.news != null && viewState.value?.trips != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
