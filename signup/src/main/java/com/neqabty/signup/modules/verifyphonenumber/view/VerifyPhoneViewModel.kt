package com.neqabty.signup.modules.verifyphonenumber.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.core.utils.AppUtils
import com.neqabty.core.utils.Resource
import com.neqabty.signup.modules.verifyphonenumber.data.model.CheckOTPBody
import com.neqabty.signup.modules.verifyphonenumber.data.model.SendOTPBody
import com.neqabty.signup.modules.verifyphonenumber.domain.entity.sendotp.OTPEntity
import com.neqabty.signup.modules.verifyphonenumber.domain.usecases.VerifyPhoneUseCase
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