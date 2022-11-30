package com.neqabty.healthcare.auth.otp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.auth.otp.data.model.CheckOTPBody
import com.neqabty.healthcare.auth.otp.data.model.SendOTPBody
import com.neqabty.healthcare.auth.otp.domain.entity.sendotp.OTPEntity
import com.neqabty.healthcare.auth.otp.domain.usecases.VerifyPhoneUseCase
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class VerifyPhoneViewModel @Inject constructor(private val verifyPhoneUseCase: VerifyPhoneUseCase): ViewModel() {

    val otp = MutableLiveData<Resource<OTPEntity>>()
    fun sendOTP(sendOTPBody: SendOTPBody){
        viewModelScope.launch(Dispatchers.IO){
            otp.postValue(Resource.loading(data = null))

            try {
                verifyPhoneUseCase.build(sendOTPBody).collect(){
                    otp.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                otp.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

//    val reCAPTCHA = MutableLiveData<Resource<String>>()
//    fun checkReCAPTCHA(token: String){
//        viewModelScope.launch(Dispatchers.IO){
//            otp.postValue(Resource.loading(data = null))
//
//            try {
//                verifyPhoneUseCase.build(token).collect(){
//                    otp.postValue(Resource.success(data = it))
//                }
//            }catch (e: Throwable){
//                otp.postValue(Resource.error(data = null, message = handleError(e)))
//            }
//        }
//    }

    val otpStatus = MutableLiveData<Resource<Boolean>>()
    fun checkOTP(checkOTPBody: CheckOTPBody){
        viewModelScope.launch(Dispatchers.IO){
            otpStatus.postValue(Resource.loading(data = null))

            try {
                verifyPhoneUseCase.build(checkOTPBody).collect(){
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
                    "خطاء فى كود التحقق"
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