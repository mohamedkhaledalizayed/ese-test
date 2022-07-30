package com.neqabty.signup.modules.verifyphonenumber.data.datasource


import com.neqabty.signup.modules.verifyphonenumber.data.api.VerifyPhoneApi
import com.neqabty.signup.modules.verifyphonenumber.data.model.CheckOTPBody
import com.neqabty.signup.modules.verifyphonenumber.data.model.SendOTPBody
import com.neqabty.signup.modules.verifyphonenumber.data.model.sendotp.OTPModel
import javax.inject.Inject

class SendOTPSource @Inject constructor(private val verifyPhoneApi: VerifyPhoneApi) {

    suspend fun sendOTP(sendOTPBody: SendOTPBody): OTPModel {
        return verifyPhoneApi.sendOTP(sendOTPBody)
    }

    suspend fun checkOTP(checkOTPBody: CheckOTPBody): Boolean{
        return verifyPhoneApi.checkOTP(checkOTPBody).status
    }

}