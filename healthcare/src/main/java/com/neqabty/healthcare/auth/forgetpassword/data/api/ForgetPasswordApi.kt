package com.neqabty.healthcare.auth.forgetpassword.data.api


import com.neqabty.healthcare.auth.forgetpassword.data.model.*
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ForgetPasswordApi {

    @POST("api/accounts/forget_password")
    suspend fun sendOTP(@Body body: SendOTPBody): SendOTPModel

    @POST("api/accounts/forget_password")
    suspend fun checkOTP(@Body body: CheckOTPBody): CheckOTPModel

    @POST("api/accounts/reset_password")
    suspend fun changePassword(@Header("Authorization") token: String, @Body body: ChangePasswordBody): ChangePasswordModel

}
