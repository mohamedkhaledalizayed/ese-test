package com.neqabty.healthcare.auth.forgetpassword.domain.repository


import com.neqabty.healthcare.auth.forgetpassword.data.model.ChangePasswordBody
import com.neqabty.healthcare.auth.forgetpassword.data.model.CheckOTPBody
import com.neqabty.healthcare.auth.forgetpassword.data.model.SendOTPBody
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.ChangePasswordEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.CheckOTPEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.SendOTPEntity
import kotlinx.coroutines.flow.Flow

interface ForgetPasswordRepository {
    fun sendOTP(body: SendOTPBody): Flow<SendOTPEntity>
    fun checkOTP(body: CheckOTPBody): Flow<CheckOTPEntity>
    fun changePassword(body: ChangePasswordBody, token: String): Flow<ChangePasswordEntity>
}