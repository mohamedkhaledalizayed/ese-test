package com.neqabty.shealth.auth.otp.domain.repository




import com.neqabty.shealth.auth.otp.data.model.CheckOTPBody
import com.neqabty.shealth.auth.otp.data.model.SendOTPBody
import com.neqabty.shealth.auth.otp.domain.entity.sendotp.OTPEntity
import kotlinx.coroutines.flow.Flow

interface VerifyPhoneRepository {
    fun sendOTP(sendOTPBody: SendOTPBody): Flow<OTPEntity>
    fun checkOTP(checkOTPBody: CheckOTPBody): Flow<Boolean>
    fun verifyRecaptcha(token: String): Flow<Boolean>
}