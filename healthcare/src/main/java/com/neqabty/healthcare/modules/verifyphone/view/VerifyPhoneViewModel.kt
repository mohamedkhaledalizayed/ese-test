package com.neqabty.healthcare.modules.verifyphone.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.modules.verifyphone.data.model.CheckOTPBody
import com.neqabty.healthcare.modules.verifyphone.data.model.SendOTPBody
import com.neqabty.healthcare.modules.verifyphone.domain.entity.sendotp.OTPEntity
import com.neqabty.healthcare.modules.verifyphone.domain.usecases.VerifyPhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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