package com.neqabty.presentation.ui.signup

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.entities.UserEntity
import com.neqabty.domain.usecases.SignupUser
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.UserUI
import com.neqabty.presentation.mappers.UserEntityUIMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class SignupViewModel @Inject constructor(private val signupUser: SignupUser) : BaseViewModel() {

    private val userEntityToUIMapper = UserEntityUIMapper()
    lateinit var userEntity: UserEntity
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<SignupViewState> = MutableLiveData()

    init {
        viewState.value = SignupViewState(isLoading = false)
    }

    fun signup(email: String, fName: String, lName: String, mobile: String, govId: String, mainSyndicateId: String, subSyndicateId: String, password: String) {
        addDisposable(signupUser.signup(email, fName, lName, mobile, govId, mainSyndicateId, subSyndicateId, password)
                .map {
                    it.let {
                        userEntity = it
                        userEntityToUIMapper.mapFrom(userEntity)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }.subscribe(
                        { onUserReceived(it) },
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
