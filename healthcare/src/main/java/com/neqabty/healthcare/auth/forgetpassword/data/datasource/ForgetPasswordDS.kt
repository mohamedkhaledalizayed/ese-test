package com.neqabty.healthcare.auth.forgetpassword.data.datasource

import com.neqabty.healthcare.auth.forgetpassword.data.api.ForgetPasswordApi
import com.neqabty.healthcare.auth.forgetpassword.data.model.*
import javax.inject.Inject

class ForgetPasswordDS @Inject constructor(private val forgetPasswordApi: ForgetPasswordApi) {

    suspend fun sendOTP(body: SendOTPBody): SendOTPModel {
        return forgetPasswordApi.sendOTP(body)
    }

    suspend fun checkOTP(body: CheckOTPBody): CheckOTPModel {
        return forgetPasswordApi.checkOTP(body)
    }

    suspend fun changePassword(body: ChangePasswordBody, token: String): ChangePasswordModel {
        return forgetPasswordApi.changePassword(body = body, token = token)
    }

}