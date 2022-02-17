package com.neqabty.signup.modules.home.presentation.view.homescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.signup.modules.home.domain.entity.SignupParams
import com.neqabty.signup.modules.home.domain.entity.UserEntity
import com.neqabty.signup.modules.home.domain.interactors.SignupUseCase
import com.neqabty.signup.modules.home.presentation.model.UserUIModel
import com.neqabty.signup.modules.home.presentation.model.mappers.toUserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val signupUseCase: SignupUseCase) :
    ViewModel() {
    val user = MutableLiveData<UserUIModel>()
    fun signup() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signupUseCase.build(
                    SignupParams(
                        entityCode = "e0005",
                        licenceNumber = "2293",
                        membershipId = "2731",
                        mobile = "01113595439",
                        nationalId = "26910160200213",
                        password = "123456"
                    )
                ).collect {
                    user.postValue(it.toUserUIModel())
                }
            } catch (e: Throwable) {

            }
        }
    }
}