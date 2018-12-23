package com.neqabty

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetUserRegistered
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class MainViewModel @Inject constructor(val getUserRegistered: GetUserRegistered) : BaseViewModel() {

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewState.value = true
    }

    fun registerUser(mobile: String, mainSyndicateId: String, subSyndicateId: String, token: String, prefs: PreferencesHelper) {
        addDisposable(getUserRegistered.getUserRegistered(mobile, mainSyndicateId, subSyndicateId, token)
                .subscribe(
                        {
                            prefs.token = token

                            prefs.isRegistered = true
                        },
                        { registerUser(mobile, mainSyndicateId, subSyndicateId, token, prefs) }
                ))
    }
}
