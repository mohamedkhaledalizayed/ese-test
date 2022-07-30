package com.neqabty.signup.modules.verifyphonenumber.data.api


import com.neqabty.signup.modules.verifyphonenumber.data.model.CheckOTPBody
import com.neqabty.signup.modules.verifyphonenumber.data.model.SendOTPBody
import com.neqabty.signup.modules.verifyphonenumber.data.model.checkotp.CheckOTPModel
import com.neqabty.signup.modules.verifyphonenumber.data.model.sendotp.OTPModel
import retrofit2.http.Body
import retrofit2.http.POST

interface VerifyPhoneApi {

    @POST("send_otp")
    suspend fun sendOTP(@Body sendOTPBody: SendOTPBody): OTPModel

    @POST("get_otp_token")
    suspend fun checkOTP(@Body checkOTPBody: CheckOTPBody): CheckOTPModel

}