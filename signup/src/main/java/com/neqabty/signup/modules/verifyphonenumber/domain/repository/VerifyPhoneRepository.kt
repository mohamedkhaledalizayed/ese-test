package com.neqabty.signup.modules.verifyphonenumber.domain.repository



import com.neqabty.signup.modules.verifyphonenumber.data.model.CheckOTPBody
import com.neqabty.signup.modules.verifyphonenumber.data.model.SendOTPBody
import com.neqabty.signup.modules.verifyphonenumber.domain.entity.sendotp.OTPEntity
import kotlinx.coroutines.flow.Flow

interface VerifyPhoneRepository {
    fun sendOTP(sendOTPBody: SendOTPBody): Flow<OTPEntity>
    fun checkOTP(checkOTPBody: CheckOTPBody): Flow<Boolean>
}