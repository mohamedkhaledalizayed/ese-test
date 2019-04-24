package com.neqabty.presentation.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.location.LocationManager
import com.neqabty.domain.entities.UserEntity
import com.neqabty.domain.usecases.LoginUser
import com.neqabty.domain.usecases.SignupUser
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.UserUI
import com.neqabty.presentation.mappers.UserEntityUIMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class LoginViewModel @Inject constructor(private val signupUser: SignupUser, private val loginUser: LoginUser) : BaseViewModel() {

    lateinit var locationManager: LocationManager

    var mCurrentPhotoPath: String = ""
    var mCurrentPhotoName: String = ""
    var photoFilePath: String = ""
    var weatherText: String = ""
    private val _photoLoaded: MutableLiveData<Boolean> = MutableLiveData()
    private val userEntityToUIMapper = UserEntityUIMapper()
    // ////////////////////////////////////////////
    lateinit var userEntity: UserEntity
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<LoginViewState> = MutableLiveData()

    init {
        viewState.value = LoginViewState(isLoading = true)
    }

    fun login(mobile: String, password: String, token: String) {
        addDisposable(loginUser.login(mobile, password, token)
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

    fun signup(
        email: String,
        fName: String,
        lName: String,
        mobile: String,
        govId: String,
        mainSyndicateId: String,
        subSyndicateId: String,
        password: String
    ) {
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
    // /////////////////////////////old

    fun setPhotoLoaded(loaded: Boolean) {
        _photoLoaded.value = loaded
    }

    val photoLoaded: LiveData<Boolean>
        get() = _photoLoaded
}
