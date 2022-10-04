package com.neqabty.signup.modules.servicessyndicate.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.core.utils.Resource
import com.neqabty.signup.modules.servicessyndicate.data.model.signup.SignUpModel
import com.neqabty.signup.modules.servicessyndicate.domain.entity.SignupParams
import com.neqabty.signup.modules.servicessyndicate.domain.interactors.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val signupUseCase: SignupUseCase) :
    ViewModel() {
    val user = MutableLiveData<Resource<SignUpModel>>()
    fun signup(data: SignupParams) {
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