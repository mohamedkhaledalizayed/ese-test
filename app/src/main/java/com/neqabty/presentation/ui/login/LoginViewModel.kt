package com.neqabty.presentation.ui.login

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.Login
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.UserUI
import com.neqabty.presentation.mappers.UserEntityUIMapper
import com.neqabty.presentation.util.PreferencesHelper

import javax.inject.Inject

class LoginViewModel @Inject constructor(val login: Login) : BaseViewModel() {

    private val userEntityToUIMapper = UserEntityUIMapper()

    // ////////////////////////////////////////////
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<LoginViewState> = MutableLiveData()

    init {
        viewState.value = LoginViewState(isLoading = false)
    }

    fun login(
            mobile: String
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(login.login(Login.PARAM_ACTION_LOGIN, mobile, "", "", "","")
                .map {
                    it.let {
                        userEntityToUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            onUserReceived(it)
                        },
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
