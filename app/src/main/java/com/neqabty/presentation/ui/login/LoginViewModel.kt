package com.neqabty.presentation.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.location.LocationManager
import com.neqabty.domain.entities.UserEntity
import com.neqabty.domain.usecases.GetVisitorLoggedIn
import com.neqabty.domain.usecases.SignupUser
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
        viewState.value = LoginViewState(isLoading = true)
    }

    fun login(mobile: String,
              token: String,
              prefs: PreferencesHelper) {
        addDisposable(getVisitorLoggedIn.login(mobile,token)
                .map {
                    it.let {
                        userEntityToUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            prefs.token = token
                            onUserReceived(it) },
                        { errorState.value = it }
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
