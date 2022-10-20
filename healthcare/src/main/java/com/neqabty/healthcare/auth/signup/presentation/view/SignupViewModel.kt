package com.neqabty.healthcare.auth.signup.presentation.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.healthcare.auth.signup.data.model.syndicatemember.UserModel
import com.neqabty.healthcare.auth.signup.domain.entity.syndicate.SyndicateListEntity
import com.neqabty.healthcare.auth.signup.domain.interactors.SignupUseCase
import com.neqabty.healthcare.auth.signup.presentation.model.UserUIModel
import com.neqabty.healthcare.auth.signup.presentation.model.mappers.toUserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val signupUseCase: SignupUseCase) :
    ViewModel() {
    val user = MutableLiveData<Resource<UserModel>>()
    fun signup(data: Any) {
        user.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signupUseCase.build(data).collect {
                    if (it.isSuccessful){
                        user.postValue(Resource.success(data = it.body()!!))
                    }else{
                        val jObjError = JSONObject(it.errorBody()!!.string()).toString()
                        user.postValue(Resource.error(data = null, message = jObjError))
                    }
                }
            } catch (e: Throwable) {
                user.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    val neqabtyMember = MutableLiveData<Resource<UserUIModel>>()
    fun signupNeqabtyMember(data: NeqabtySignupBody) {
        neqabtyMember.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signupUseCase.build(data).collect {
                    neqabtyMember.postValue(Resource.success(data = it.toUserUIModel()))
                }
            } catch (e: Throwable) {
                neqabtyMember.postValue(Resource.error(data = null, message = handleError(e)))
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