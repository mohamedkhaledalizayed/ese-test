package com.neqabty.login.modules.login.presentation.view.homescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.login.core.utils.AppUtils
import com.neqabty.login.core.utils.Resource
import com.neqabty.login.modules.login.domain.entity.UserEntity
import com.neqabty.login.modules.login.domain.interactors.LoginUseCase
import com.neqabty.login.modules.login.presentation.model.UserUIModel
import com.neqabty.login.modules.login.presentation.model.mappers.toUserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :
    ViewModel() {
    val user = MutableLiveData<Resource<UserUIModel>>()
    fun login(mobile: String, password: String) {
        user.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loginUseCase.build(mobile, password).collect {
                    user.postValue(Resource.success(data = it.toUserUIModel()))
                }
            } catch (e: Throwable) {
                user.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}