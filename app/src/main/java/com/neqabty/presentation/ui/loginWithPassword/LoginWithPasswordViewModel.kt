package com.neqabty.presentation.ui.loginWithPassword

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.Login
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.UserUI
import com.neqabty.presentation.mappers.UserEntityUIMapper
import com.neqabty.presentation.util.PreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginWithPasswordViewModel @Inject constructor(val login: Login) : BaseViewModel() {

    private val userEntityToUIMapper = UserEntityUIMapper()

    // ////////////////////////////////////////////
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<LoginWithPasswordViewState> = MutableLiveData()

    init {
        viewState.value = LoginWithPasswordViewState()
    }

    fun login(
            mobile: String,
            token: String,
            prefs: PreferencesHelper,
            password: String
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(login.login(Login.PARAM_ACTION_VERIFIED_LOGIN, mobile, "", token, prefs.token, password)
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

    private fun onUserReceived(user: UserUI) {

        val newViewState = viewState.value?.copy(
                isLoading = false,
                user = user)

        viewState.value = newViewState
    }

}
