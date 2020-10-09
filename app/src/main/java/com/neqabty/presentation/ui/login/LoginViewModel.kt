package com.neqabty.presentation.ui.login

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetVisitorLoggedIn
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.UserUI
import com.neqabty.presentation.mappers.UserEntityUIMapper
import com.neqabty.presentation.util.PreferencesHelper

import javax.inject.Inject

class LoginViewModel @Inject constructor(val getVisitorLoggedIn: GetVisitorLoggedIn) : BaseViewModel() {

    private val userEntityToUIMapper = UserEntityUIMapper()
    // ////////////////////////////////////////////
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<LoginViewState> = MutableLiveData()

    init {
        viewState.value = LoginViewState()
    }

    fun login(
        mobile: String,
        token: String,
        prefs: PreferencesHelper
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(getVisitorLoggedIn.login(mobile, token)
                .map {
                    it.let {
                        userEntityToUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            prefs.token = token
                            onUserReceived(it) },
                        { errorState.value = handleError(it) }
                )
        )
    }

    private fun onUserReceived(user: UserUI) {

        val newViewState = viewState.value?.copy(
                isLoading = false,
                user = user)

        viewState.value = newViewState
    }
}
