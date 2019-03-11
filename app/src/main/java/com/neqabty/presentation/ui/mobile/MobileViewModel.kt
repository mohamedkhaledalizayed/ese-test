package com.neqabty.presentation.ui.mobile

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetUserRegistered
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.DoctorEntityUIMapper
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class MobileViewModel @Inject constructor(val getUserRegistered: GetUserRegistered) : BaseViewModel() {

    private val doctorEntityUIMapper = DoctorEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MobileViewState> = MutableLiveData()

    init {
        viewState.value = MobileViewState()
    }

    fun registerUser(mobile: String, mainSyndicateId: Int, subSyndicateId: Int, token: String, prefs: PreferencesHelper, userNumber: String) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(getUserRegistered.getUserRegistered(mobile, mainSyndicateId, subSyndicateId, token,userNumber)
                .subscribe(
                        {
                            prefs.token = token
                            prefs.mobile = mobile
                            prefs.isRegistered = true
                            viewState.value = viewState.value?.copy(isLoading = false,isSuccessful = true)
                        },
                        { registerUser(mobile, mainSyndicateId, subSyndicateId, token, prefs,userNumber) }
                ))
    }
}
