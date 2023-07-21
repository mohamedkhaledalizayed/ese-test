package com.neqabty.healthcare.auth.forgetpassword.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.auth.forgetpassword.data.model.CheckOTPBody
import com.neqabty.healthcare.auth.forgetpassword.data.model.SendOTPBody
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.CheckOTPEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.SendOTPEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.usecases.CheckOTP
import com.neqabty.healthcare.auth.forgetpassword.domain.usecases.SendOTP
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(private val sendOTP: SendOTP,
 private val checkOTP: CheckOTP): ViewModel() {

    val otp = MutableLiveData<Resource<SendOTPEntity>>()
    fun sendOTP(body: SendOTPBody){
        viewModelScope.launch(Dispatchers.IO){
            otp.postValue(Resource.loading(data = null))

            try {
                sendOTP.build(body).collect {
                    otp.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                otp.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    val otpStatus = MutableLiveData<Resource<CheckOTPEntity>>()
    fun checkOTP(body: CheckOTPBody){
        viewModelScope.launch(Dispatchers.IO){
            otpStatus.postValue(Resource.loading(data = null))

            try {
                checkOTP.build(body).collect {
                    otpStatus.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                otpStatus.postValue(Resource.error(data = null, message = handleError(e)))
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