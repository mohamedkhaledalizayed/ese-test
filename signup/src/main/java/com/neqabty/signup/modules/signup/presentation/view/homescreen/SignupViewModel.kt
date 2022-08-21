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
import retrofit2.HttpException
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
                user.postValue(Resource.error(data = null, message = handleError(e)))
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
                user.postValue(Resource.error(data = null, message = handleError(e)))
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
                syndicateList.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "يوجد حساب مسجل بهذه البيانات"
                }
                401 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                403 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                404 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                500 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                else -> {
                    throwable.message!!
                }
            }
        } else {
            throwable.message!!
        }
    }

}