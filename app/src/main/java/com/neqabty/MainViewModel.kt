package com.neqabty

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetUserLoggedIn
import com.neqabty.domain.usecases.GetVisitorLoggedIn
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.UserUI
import com.neqabty.presentation.mappers.UserEntityUIMapper
import com.neqabty.presentation.util.PreferencesHelper
import javax.inject.Inject

class MainViewModel @Inject constructor(val getUserLoggedIn: GetUserLoggedIn, val getVisitorLoggedIn: GetVisitorLoggedIn) : BaseViewModel() {
    private val userEntityToUIMapper = UserEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        viewState.value = MainViewState()
    }

    fun registerUser(
            mobile: String,
            mainSyndicateId: Int,
            subSyndicateId: Int,
            token: String,
            prefs: PreferencesHelper,
            userNumber: String
    ) {
        addDisposable(getUserLoggedIn.getUserRegistered(mobile, mainSyndicateId, subSyndicateId, token, userNumber)
                .subscribe(
                        {
                            prefs.token = token
                            prefs.isRegistered = true
                            viewState.value?.copy(
                                    isLoading = false)
                        },
                        { errorState.value = handleError(it) }
                ))
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
