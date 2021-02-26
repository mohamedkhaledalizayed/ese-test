package com.neqabty.presentation.ui.signup

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.SignupUser
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.UserEntityUIMapper
import com.neqabty.presentation.util.PreferencesHelper
import javax.inject.Inject

class SignupViewModel @Inject constructor(val signup: SignupUser) : BaseViewModel() {

    private val userEntityToUIMapper = UserEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<SignupViewState> = MutableLiveData()

    init {
        viewState.value = SignupViewState()
    }

    fun registerUser(
            userNumber: String,
            mobile: String,
            natID: String,
            newFirebaseToken: String,
            prefs: PreferencesHelper
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(signup.signup(userNumber, mobile, natID, newFirebaseToken, prefs.token)
                .map {
                    it.let {
                        userEntityToUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            prefs.token = newFirebaseToken
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = true, user = it)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = false)
                            errorState.value = handleError(it)
                        }
                ))
    }
}
