package com.neqabty

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetUserLoggedIn
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.util.PreferencesHelper

import javax.inject.Inject

class MainViewModel @Inject constructor(val getUserLoggedIn: GetUserLoggedIn) : BaseViewModel() {

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewState.value = false
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
                            viewState.value = true
                        },
                        { registerUser(mobile, mainSyndicateId, subSyndicateId, token, prefs, userNumber) }
                ))
    }
}
