package com.neqabty.signup.modules.signup.presentation.view.homescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.core.utils.AppUtils
import com.neqabty.core.utils.Resource
import com.neqabty.signup.modules.signup.data.model.NeqabtySignupBody
import com.neqabty.signup.modules.signup.domain.entity.SignupParams
import com.neqabty.signup.modules.signup.domain.entity.syndicate.SyndicateListEntity
import com.neqabty.signup.modules.signup.domain.interactors.SignupUseCase
import com.neqabty.signup.modules.signup.presentation.model.UserUIModel
import com.neqabty.signup.modules.signup.presentation.model.mappers.toUserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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