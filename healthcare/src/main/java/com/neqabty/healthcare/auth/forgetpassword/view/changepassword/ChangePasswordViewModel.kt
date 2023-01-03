package com.neqabty.healthcare.auth.forgetpassword.view.changepassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.auth.forgetpassword.data.model.ChangePasswordBody
import com.neqabty.healthcare.auth.forgetpassword.data.model.CheckOTPBody
import com.neqabty.healthcare.auth.forgetpassword.data.model.SendOTPBody
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.ChangePasswordEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.CheckOTPEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.SendOTPEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.usecases.ChangePassword
import com.neqabty.healthcare.auth.forgetpassword.domain.usecases.CheckOTP
import com.neqabty.healthcare.auth.forgetpassword.domain.usecases.SendOTP
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(private val changePassword: ChangePassword): ViewModel() {

    val status = MutableLiveData<Resource<ChangePasswordEntity>>()
    fun changePassword(body: ChangePasswordBody, token: String){
        viewModelScope.launch(Dispatchers.IO){
            status.postValue(Resource.loading(data = null))
            try {
                changePassword.build(body, token).collect(){
                    status.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                status.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                226 -> {
                    "هذا الرقم مفعل من قبل برجاء تسجيل الدخول مرة اخرى."
                }
                400 -> {
                    "حدث خطأ"
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