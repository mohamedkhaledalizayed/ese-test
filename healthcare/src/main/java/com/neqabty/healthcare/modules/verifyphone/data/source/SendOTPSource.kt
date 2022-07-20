package com.neqabty.healthcare.modules.verifyphone.data.source

import com.neqabty.healthcare.modules.verifyphone.data.api.VerifyPhoneApi
import com.neqabty.healthcare.modules.verifyphone.data.model.CheckOTPBody
import com.neqabty.healthcare.modules.verifyphone.data.model.SendOTPBody
import com.neqabty.healthcare.modules.verifyphone.data.model.sendotp.OTPModel
import javax.inject.Inject

class SendOTPSource @Inject constructor(private val verifyPhoneApi: VerifyPhoneApi) {

    suspend fun sendOTP(sendOTPBody: SendOTPBody): OTPModel{
        return verifyPhoneApi.sendOTP(sendOTPBody)
    }

    suspend fun checkOTP(checkOTPBody: CheckOTPBody): Boolean{
        return verifyPhoneApi.checkOTP(checkOTPBody).status
    }

}