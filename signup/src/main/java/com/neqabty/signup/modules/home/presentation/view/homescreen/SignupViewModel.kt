package com.neqabty.signup.modules.home.presentation.view.homescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.signup.core.utils.AppUtils
import com.neqabty.signup.core.utils.Resource
import com.neqabty.signup.modules.home.data.model.NeqabtySignupBody
import com.neqabty.signup.modules.home.domain.entity.SignupParams
import com.neqabty.signup.modules.home.domain.entity.UserEntity
import com.neqabty.signup.modules.home.domain.entity.syndicate.SyndicateListEntity
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
    val user = MutableLiveData<Resource<UserUIModel>>()
    fun signup(data: SignupParams) {
        user.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signupUseCase.build(data).collect {
                    user.postValue(Resource.success(data = it.toUserUIModel()))
                }
            } catch (e: Throwable) {
                user.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val neqabtyMember = MutableLiveData<Resource<UserUIModel>>()
    fun signupNeqabtyMember(data: NeqabtySignupBody) {
        user.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signupUseCase.build(data).collect {
                    user.postValue(Resource.success(data = it.toUserUIModel()))
                }
            } catch (e: Throwable) {
                user.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val syndicateList = MutableLiveData<Resource<List<SyndicateListEntity>>>()
    fun getSyndicateList() {
        syndicateList.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signupUseCase.build().collect {
                    syndicateList.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                syndicateList.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}