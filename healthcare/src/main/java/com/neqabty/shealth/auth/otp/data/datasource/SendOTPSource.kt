package com.neqabty.shealth.auth.otp.data.datasource


import com.neqabty.shealth.auth.otp.data.api.VerifyPhoneApi
import com.neqabty.shealth.auth.otp.data.model.CheckOTPBody
import com.neqabty.shealth.auth.otp.data.model.SendOTPBody
import com.neqabty.shealth.auth.otp.data.model.sendotp.OTPModel
import javax.inject.Inject

class SendOTPSource @Inject constructor(private val verifyPhoneApi: VerifyPhoneApi) {

    suspend fun sendOTP(sendOTPBody: SendOTPBody): OTPModel {
        return verifyPhoneApi.sendOTP(sendOTPBody)
    }

    suspend fun checkOTP(checkOTPBody: CheckOTPBody): Boolean{
        return verifyPhoneApi.checkOTP(checkOTPBody).status
    }

    suspend fun verifyRecaptcha(token: String): Boolean{
        return verifyPhoneApi.verifyRecaptcha(token = token).success
    }

}