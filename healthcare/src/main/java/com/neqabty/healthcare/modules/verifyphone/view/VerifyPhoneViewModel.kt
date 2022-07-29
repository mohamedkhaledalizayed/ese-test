package com.neqabty.healthcare.modules.verifyphone.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.modules.verifyphone.data.model.CheckOTPBody
import com.neqabty.healthcare.modules.verifyphone.data.model.CheckPhoneBody
import com.neqabty.healthcare.modules.verifyphone.data.model.SendOTPBody
import com.neqabty.healthcare.modules.verifyphone.domain.entity.sendotp.OTPEntity
import com.neqabty.healthcare.modules.verifyphone.domain.usecases.VerifyPhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class VerifyPhoneViewModel @Inject constructor(private val verifyPhoneUseCase: VerifyPhoneUseCase): ViewModel() {

    val accountStatus = MutableLiveData<Resource<String>>()
    fun checkAccount(checkPhoneBody: CheckPhoneBody){
        viewModelScope.launch(Dispatchers.IO){
            accountStatus.postValue(Resource.loading(data = null))

            try {
                verifyPhoneUseCase.build(checkPhoneBody).collect(){
                    accountStatus.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                accountStatus.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                401 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                403 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                404 -> {
                    "404"
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

    val otp = MutableLiveData<Resource<OTPEntity>>()
    fun sendOTP(sendOTPBody: SendOTPBody){
        viewModelScope.launch(Dispatchers.IO){
            otp.postValue(Resource.loading(data = null))

            try {
                verifyPhoneUseCase.build(sendOTPBody).collect(){
                    otp.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                otp.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val otpStatus = MutableLiveData<Resource<Boolean>>()
    fun checkOTP(checkOTPBody: CheckOTPBody){
        viewModelScope.launch(Dispatchers.IO){
            otpStatus.postValue(Resource.loading(data = null))

            try {
                verifyPhoneUseCase.build(checkOTPBody).collect(){
                    otpStatus.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                otpStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}